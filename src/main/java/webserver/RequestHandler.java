package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;
import util.SingletonContainer;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String commonPath = "./src/main/resources/templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();

            logger.debug("request first line = {}", line);

            if (line != null) {
                String[] parsedUrl = RequestParser.separateUrls(line);
                String httpMethod = parsedUrl[0];
                String resourceUrl = parsedUrl[1];

                String returnUrl = "/index.html";
                if (resourceUrl.startsWith("/user")) {
                    returnUrl = SingletonContainer.getUserController().mapToFunctions(httpMethod, resourceUrl);
                }

                logger.debug("request: [{}], url: [{}]", line, returnUrl);

                while (!line.equals("")) {
                    line = br.readLine();
                    logger.debug("request: {}", line);
                }

                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File(commonPath + returnUrl).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

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
