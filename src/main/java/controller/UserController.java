package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

import java.io.IOException;

import static webserver.protocol.Method.GET;

public class UserController implements Controller{
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final String ROOT = "/user";

    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (GET.equals(httpRequest.getMETHOD())) {

                if (httpRequest.isPath(ROOT + "/form.html")) {
                    httpResponse.forward(httpRequest.getPATH())
                            .response();
                }

                if (httpRequest.isPath(ROOT + "/create")) {
                    join(httpRequest, httpResponse);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
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
