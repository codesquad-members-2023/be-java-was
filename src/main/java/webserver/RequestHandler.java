package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

            String line = HttpRequest.startLine(in);
            logger.debug("line = {} ",line);

            if(line==null){
                return;
            }

            HttpRequest httpRequest = new HttpRequest();
            String url = httpRequest.getUrl(line);

            HttpResponse httpResponse = new HttpResponse(out);
            httpResponse.forward(url);

            logger.debug("url = {} ", url);

            if (url.startsWith("/user/create")) {
                Map<String, String> params = httpRequest.parseQueryString(url);
                httpRequest.addUser(params);
                httpResponse.redirect("/index.html");
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
