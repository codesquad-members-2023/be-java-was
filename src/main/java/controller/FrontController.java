package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

public abstract class FrontController implements Controller {
    protected Logger logger = LoggerFactory.getLogger(FrontController.class);

    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    public String service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String returnPage = "";
        try {
            switch (httpRequest.getMethod()) {
                case GET:
                    returnPage = doGet(httpRequest, httpResponse);
                case POST:
                    returnPage = doPost(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return returnPage;
    }

    protected abstract String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
    protected abstract String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
