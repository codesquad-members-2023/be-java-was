package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import config.AppConfig;
import controller.URLController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequest;
import util.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final URLController urlController = AppConfig.urlController();

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();

            HttpRequest httpRequest = new HttpRequest(line);
            HttpResponse httpResponse = new HttpResponse();

            String path = httpRequest.getUrl();
            path = urlController.mapUrl(path, httpRequest, httpResponse);

            httpResponse.processResponse(path, httpResponse, out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
