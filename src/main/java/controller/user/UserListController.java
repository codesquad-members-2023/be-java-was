package controller.user;

import controller.FrontController;
import db.Database;
import service.UserService;
import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.IOException;

public class UserListController extends FrontController {

    UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        model.addObject("user", Database.findAll());
        return "/user/list.html";
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }

}
