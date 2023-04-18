package controller;

import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;

import static util.Constants.BASE_PATH;

public class DefaultController extends FrontController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        if (httpRequest.isPath(BASE_PATH)) {
            return "/index.html";
        }

        return httpRequest.getUrlPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }
}
