package controller;

import annotation.ExceptionHandler;
import annotation.MethodType;
import annotation.RequestMapping;
import exception.UserInfoException;
import request.HttpRequest;
import response.HttpResponse;

@RequestMapping(url = "/")
public class HomeController extends Controller {

    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
