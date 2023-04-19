package controller.interceptor;

import exception.ExceptionHandler;
import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

public class PreHandler {

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, Model model) {
        checkLoginUser(httpRequest, httpResponse, model);
    }

    private static void checkLoginUser(HttpRequest httpRequest, HttpResponse httpResponse, Model model) {
        if(httpRequest.isSessionValid()) {
            httpRequest.getSession();
            model.addObject("user", httpRequest.getSession());
        }
    }

    public static PreHandler create() {
        return new PreHandler();
    }
}
