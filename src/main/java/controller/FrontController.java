package controller;

import config.AppConfig;
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
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            switch (httpRequest.getMethod()) {
                case GET:
                    doGet(httpRequest, httpResponse);
                    break;
                case POST:
                    doPost(httpRequest, httpResponse);
                    break;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
