package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

import static webserver.protocol.Method.GET;

public class ViewController implements Controller{
    private Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {


                if (httpRequest.isPath("/")) {
                    httpResponse.forward("/index.html")
                            .response();
                    return;
                }

                httpResponse.forward(httpRequest.getPath()) // 그 외의 경로는 temmplate에서 경로에 맞는 문서를 반환한다.
                        .response();
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
