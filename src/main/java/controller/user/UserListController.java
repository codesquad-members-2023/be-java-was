package controller.user;

import controller.FrontController;
import db.Database;
import service.UserService;
import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static webserver.RequestHandler.logger;

public class UserListController extends FrontController {

    UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        logger.debug("sessionKey = {}", httpRequest.getSessionKey());
        logger.debug("isSessionValid = {}", String.valueOf(httpRequest.isSessionValid()));
        if (!httpRequest.isSessionValid()) {
            throw new AccessDeniedException("로그인 후 이용 가능합니다.");
        }

        model.addObject("user", Database.findAll());
        return "/user/list.html";
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }

}
