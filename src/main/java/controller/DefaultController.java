package controller;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.StatusCode;

import java.io.IOException;

public class DefaultController extends FrontController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.isPath("/")) {
            httpResponse.forward("/index.html").response();
            return "/index.html";
        }

        httpResponse.forward(httpRequest.getPath()).response();
        return httpRequest.getPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }
}
