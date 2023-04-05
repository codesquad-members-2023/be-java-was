package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.ContentType;

import java.io.IOException;

import static webserver.protocol.Method.GET;

public class ViewController implements Controller{
    private Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {

            if (GET.equals(httpRequest.getMethod())) {

                if (httpRequest.getPath().equals("/")) {
                    forwardIndex(httpRequest, httpResponse);
                }

                if (StyleType.anyMatchStyle(httpRequest.getPath()).isPresent()) {   // style 요청을 받은 경우
                    logger.info("path = {}", httpRequest.getPath());

                }

                // 그 외의 경로는 temmplate에서 경로에 맞는 문서를 반환한다.
                httpResponse.forward(httpRequest.getPath())
                        .response();
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
