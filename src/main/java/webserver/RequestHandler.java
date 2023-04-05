package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringTokenizer;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequestUtils httpRequestUtils = new HttpRequestUtils(br);

//            String requestLine = br.readLine();
//            logger.debug("request line : {}", requestLine);
//            String requestHeader;
//            while(!(requestHeader = br.readLine()).equals("")) {
//                logger.debug("header : {}", requestHeader);
//            }

            String url = httpRequestUtils.getRequestLineHttpURI();
            logger.debug("request url : {}", url);


            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + url).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
            connection.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
