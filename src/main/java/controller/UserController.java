package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import static webserver.protocol.MethodType.GET;

public class UserController implements Controller{
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final String ROOT = "/user";

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (GET.equals(httpRequest.getMethod())) {

            if (httpRequest.getPath().equals(ROOT+"/create")) {
                join(httpRequest, httpResponse);
            }
        }

    }

    private void join(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        User user = new User(userId, password, name, email);

        Database.addUser(user);

        logger.info("[WELCOME] NEW USER = {}", user);

        httpResponse.redirect("/")
                .response();
    }


}
