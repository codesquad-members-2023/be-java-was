package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import webserver.ContentTypeParser;

import java.io.DataOutputStream;
import java.io.IOException;

public class RedirectResponseBuilder implements HttpResponseBuilder {

    private final String serverIP = "http://localhost:8080";

    private final String welcomePage = "/index.html";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private HttpRequest httpRequest;

    public RedirectResponseBuilder(HttpRequest httpRequest) throws IOException {
        this.httpRequest = httpRequest;
    }

    @Override
    public void buildResponse(DataOutputStream dos, String extension, byte[] body) {
        try {
            logger.info("redirect url={}", serverIP + welcomePage);

            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + serverIP + welcomePage + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
