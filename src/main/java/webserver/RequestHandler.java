package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private static final String PATH = "src/main/resources/templates";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String line = HttpRequest.startLine(in);

            if (line == null) { // line이 null일때 무시
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
                httpResponse.response302Header("/index.html");
            }

            byte[] body = Files.readAllBytes(new File(PATH + url).toPath());
            httpResponse.response200Header(body.length);
            httpResponse.responseBody(body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getDefaultPath(String path){
        if(path.equals("/")){
            return "/index.html";
        }
        return path;
    }
}
