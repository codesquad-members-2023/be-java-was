package controller;

import db.Database;
import model.User;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

public class UserController extends FrontController {
    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.finalPath("/form.html")) {
            httpResponse.forward(httpRequest.getPath())
                    .response();
        }
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.finalPath("/create")) {
            join(httpRequest, httpResponse);
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
