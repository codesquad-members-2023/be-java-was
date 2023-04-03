package webserver;

import java.io.*;
import java.net.Socket;

import config.AppConfig;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private AppConfig appConfig = new AppConfig();

    private final Socket connection;
    private final UserController userController = appConfig.userController();

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();

            String[] splitHeader = line.split(" ");

            String uriPath = getURIPath(splitHeader);
            String path = getPath(uriPath);
            String httpMethod = splitHeader[0];

            String controllerName = findController(path);

            if (controllerName.equals("user")) {
                path = userController.process(httpMethod, path, uriPath);
            }

            byte[] body = getClass().getResourceAsStream("/templates" + path).readAllBytes();

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getURIPath(String[] splitHeader) {
        return splitHeader[1];
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
