package controller;

import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

public class DefaultController extends FrontController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.isPath("/")) {
            httpResponse.forward("/index.html").response();
            return "/index.html";
        }

        httpResponse.forward(httpRequest.getUri()).response();
        return httpRequest.getUri();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }
}
