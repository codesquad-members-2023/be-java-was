package webserver;

//import config.AppConfig;
import controller.FrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

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

            // InputStreamReader 통해서 HttpRequest 클래스를 만든다
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequest httpRequest = new HttpRequest(br);

            // DataOutputStream 통해서 HttpResponse 클래스를 만든다
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos);

            // FrontController 클래스를 만든다
            FrontController frontController = new FrontController();
            frontController.handleRequest(httpRequest, httpResponse);

            connection.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
