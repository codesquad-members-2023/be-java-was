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

    // TODO 테스트를 할 수가 없어...
    private void join(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBodyParameter("userId");
        String password = httpRequest.getBodyParameter("password");
        String name = httpRequest.getBodyParameter("name");
        String email = httpRequest.getBodyParameter("email");
        User user = new User(userId, password, name, email);

        Database.addUser(user);

        logger.info("[WELCOME] NEW USER = {}", user);

        httpResponse.redirect("/")
                .response();
    }


}
