package webserver;

import java.io.*;
import java.net.Socket;

import controller.HandlerMapper;
import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.ViewResolver;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

public class RequestHandler implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = HttpResponse.accepted();
            ViewResolver viewResolver = ViewResolver.create(out);

            Controller controller = HandlerMapper.getController(httpRequest.getUrlPath());
            ModelAndView mv = controller.service(httpRequest, httpResponse);

            viewResolver.render(httpResponse, mv);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }



}
