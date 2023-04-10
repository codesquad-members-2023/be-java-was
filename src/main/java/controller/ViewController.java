package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.ContentType;

import java.io.IOException;

import static webserver.protocol.Method.GET;

public class ViewController extends FrontController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.isPath("/")) {
            httpResponse.forward("/index.html")
                    .response();
            return;
        }

        httpResponse.forward(httpRequest.getPath()) // 그 외의 경로는 temmplate에서 경로에 맞는 문서를 반환한다.
                .response();
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        super.doPost(httpRequest, httpResponse);
    }
}
