package controller;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import service.UserService;
import session.SessionManager;
import type.RequestMethod;

import java.util.Map;

@RequestMapping(path = "/user")
public class UserController implements Controller {

    private final UserService userService = new UserService();
    private SessionManager sessionManager;

    public UserController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void create(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        httpResponse.sendRedirect("/");
        httpResponse.setStatus(HttpResponse.SC_SEE_OTHER);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public void login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = userService.login(userId, password);

        if (user == null) {
            httpResponse.sendRedirect("/user/login_failed.html");
            httpResponse.setStatus(HttpResponse.SC_UNAUTHORIZED);
            return;
        }
        sessionManager.createSession(user,httpResponse);
        httpResponse.sendRedirect("/");
    }

    @RequestMapping()
    public void show(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getPathInfo();
        httpResponse.addHeader("Request-URL", requestUrl);
    }
}
