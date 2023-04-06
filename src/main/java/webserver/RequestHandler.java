package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.HttpResponseUtils;
import util.stylesheetUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // 모든 리퀘스트 출력 & 첫 라인 리턴
            String line = HttpRequestUtils.getStartLine(br);
            if (line == null) {
                return;
            }

//            String method = HttpRequestUtils.getMethod(line); // method 설정
            String url = HttpRequestUtils.getUrl(line); // path 설정

            // GET: stylesheet
            String contentType = stylesheetUtils.getContentType(url);
            String pathName = stylesheetUtils.getPathName(url);

            // GET: join
            if (url.startsWith("/user/create?")) {
                url = HttpRequestUtils.joinWithGET(url);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(pathName + url).toPath());
            HttpResponseUtils.response200Header(dos, body.length, contentType);
            HttpResponseUtils.responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
