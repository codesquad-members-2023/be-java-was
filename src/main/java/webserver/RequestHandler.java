package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String templatePath = "./src/main/resources/templates";
    private static final String staticPath = "./src/main/resources/static";

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

            HttpRequestHeader httpRequestHeader = new HttpRequestHeader(br);
            HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();

            String returnUrl = httpRequestHeader.getValueByName("returnUrl");

            DataOutputStream dos = new DataOutputStream(out);

            File f = new File(templatePath + returnUrl);
            if (!f.exists()) {
                f = new File(staticPath + returnUrl);
            }

            byte[] body = Files.readAllBytes(f.toPath());
            httpResponseBuilder.response200Header(dos, body.length, httpRequestHeader.getExtension());
            httpResponseBuilder.responseBody(dos, body);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
