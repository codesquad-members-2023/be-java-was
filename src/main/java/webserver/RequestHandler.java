package webserver;

import db.Database;
import model.RequestInfo;
import model.Stylesheet;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.HttpResponseUtils;
import util.StylesheetUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequestUtils reqUtils = new HttpRequestUtils();

            // 첫 라인 리턴
            String startLine = reqUtils.getStartLine(br);
            logger.debug("startLine: {}", startLine);

            RequestInfo reInfo = new RequestInfo(reqUtils.getMethod(startLine), // method 설정
                    reqUtils.getUrl(startLine), // path 설정
                    reqUtils.getRequestHeaders(br)); // header 정보 => Map<String, String>

            // GET: stylesheet
            Stylesheet stylesheet = new Stylesheet(StylesheetUtils.getContentType(reInfo.getUrl()),
                    StylesheetUtils.getPathName(reInfo.getUrl()) + reInfo.getUrl());

            // POST: join
            DataOutputStream dos = new DataOutputStream(out);
            if (reInfo.comparingMethodUrl("POST", "/user/create")) {
                int contentLength = Integer.parseInt(reInfo.getHeaderData("Content-Length"));
                String requestBody = reqUtils.getRequestBody(br, contentLength);
                logger.debug("requestBody: {}", requestBody);

                User user = reqUtils.joinWithPOST(requestBody);
                Database.addUser(user);
                logger.debug("User: {}", user);

                HttpResponseUtils.response302Header(dos);
            } else {
                byte[] body = Files.readAllBytes(new File(stylesheet.getPathName()).toPath());
                HttpResponseUtils.response200Header(dos, body.length, stylesheet.getContentType());
                HttpResponseUtils.responseBody(dos, body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
