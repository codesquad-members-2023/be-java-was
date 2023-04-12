package controller;

import annotation.RequestMapping;
import request.HttpRequest;
import response.HttpResponse;

@RequestMapping(url = "/")
public class HomeController implements Controller {
    @Override
    public String doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
