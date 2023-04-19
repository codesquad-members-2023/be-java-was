package controller.user;

import controller.FrontController;
import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;

import static util.Constants.REDIRECT_BASE;

public class UserLogOutController extends FrontController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        if (httpRequest.isSessionValid()) {
            httpResponse.setCookieExpired("sid");
        }
        return REDIRECT_BASE;
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }
}
