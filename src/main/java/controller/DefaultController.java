package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.StatusCode;

import java.io.IOException;

public class DefaultController extends FrontController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.isPath("/")) {
            httpResponse.forward(StatusCode.OK, "/index.html").response();
            return "/index.html";
        }

        httpResponse.forward(StatusCode.OK, httpRequest.getPath()).response();
        return httpRequest.getPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }
}
