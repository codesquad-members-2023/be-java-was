package webserver;

import java.io.*;
import java.net.Socket;

import controller.HandlerMapping;
import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.HttpRequest;
import protocol.HttpResponse;

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

            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            HandlerMapping handlerMapping = new HandlerMapping();
            Controller controller = handlerMapping.getController(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }



}
