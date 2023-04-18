package controller.user;

import controller.FrontController;
import model.User;
import service.UserService;
import util.ProtocolParser;
import view.Model;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;
import webserver.protocol.response.StatusCode;
import webserver.protocol.session.Session;
import webserver.protocol.session.SessionStore;

import java.io.IOException;
import java.util.Map;

public class UserLoginController extends FrontController {

    UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
        return null;
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return login(httpRequest, httpResponse);
    }

    private String login(HttpRequest httpRequest, HttpResponse httpResponse) throws IllegalArgumentException {
        Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
        User loginedUser = userService.login(parameter);

        SessionStore.addSession(new Session(loginedUser));
        return "redirect:/";
    }
}
