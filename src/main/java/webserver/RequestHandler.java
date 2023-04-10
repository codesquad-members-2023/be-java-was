package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import config.AppConfig;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private final String USER = "user";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final AppConfig appConfig = new AppConfig();
    private final UserController userController = appConfig.makeUserController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            if (line == null) {
                return;
            }
            logger.debug("request line: {}", line);

            String[] splitRequestLine = line.split(" ");        // httpMethod / URI / httpVersion
            logger.debug("splitRequestLine: {}", Arrays.toString(splitRequestLine));
            String uri = getUri(splitRequestLine);
            logger.debug("uri: {}", uri);
            String path = getPath(uri);
            logger.debug("path: {}", path);
            String httpMethod = splitRequestLine[0];

            String selectedController = findController(path);

            if (selectedController.equals(USER)) {
                path = userController.process(httpMethod, path, uri);
                logger.debug("path: {}", path);
            }

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + path).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
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

    private String getUri(String[] splitRequestLine) {
        return splitRequestLine[1];
    }

    private String getPath(String uriPath) {
        String[] split = uriPath.split("\\?");
        return split[0];
    }

    private String findController(String path) {
        String[] split = path.split("/");
        return split[1];
    }
}
