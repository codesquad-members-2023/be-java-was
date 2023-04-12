package controller;

import annotation.MethodType;
import annotation.RequestMapping;
import request.HttpRequest;
import response.HttpResponse;

@RequestMapping(url = "/")
public class HomeController implements Controller {

    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
