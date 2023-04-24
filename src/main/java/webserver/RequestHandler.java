package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import configure.ApplicationContext;
import servlet.HttpRequest;
import servlet.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DispatcherServlet;
import util.HttpRequestParser;

public class RequestHandler implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequestParser httpRequestParser;
    private DispatcherServlet dispatcherServlet;


    public RequestHandler(Socket connectionSocket, ApplicationContext applicationContext) {
        this.connection = connectionSocket;
        this.httpRequestParser = (HttpRequestParser) applicationContext.getBean("httpRequestParser");
        this.dispatcherServlet= (DispatcherServlet) applicationContext.getBean("dispatcherServlet");
    }

    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader( in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = httpRequestParser.parse(br);
            HttpResponse httpResponse = new HttpResponse(out);

            dispatcherServlet.service(httpRequest, httpResponse);


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
