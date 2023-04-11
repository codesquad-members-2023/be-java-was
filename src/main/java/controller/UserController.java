package controller;

import db.Database;
import model.User;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

import static controller.HandlerMapping.USER_URL;

public class UserController extends FrontController {
    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward(httpRequest.getPath()).response();
        return httpRequest.getPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isPath(USER_URL + "/create")) {
            return join(httpRequest, httpResponse);
        }
        return httpRequest.getPath();
    }

    private String join(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBodyParameter("userId");
        String password = httpRequest.getBodyParameter("password");
        String name = httpRequest.getBodyParameter("name");
        String email = httpRequest.getBodyParameter("email");
        User user = new User(userId, password, name, email);

        Database.addUser(user);

        logger.info("[WELCOME] NEW USER = {}", user);

        httpResponse.redirect("/").response();

        return "redirect:/";
    }


}
