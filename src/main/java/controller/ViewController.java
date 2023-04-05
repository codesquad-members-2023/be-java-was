package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

import static webserver.protocol.MethodType.GET;

public class ViewController implements Controller{
    private Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {

            if (GET.equals(httpRequest.getMethod())) {

                if (httpRequest.getPath().equals("/")) {
                    forwardIndex(httpRequest, httpResponse);
                }

                // 그 외의 경로는 temmplate에서 경로에 맞는 문서를 반환한다.
                httpResponse.forward(httpRequest.getPath())
                        .response();
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void forwardIndex(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward("/index.html")
                .response();
    }
}
