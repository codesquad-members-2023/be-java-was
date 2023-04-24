package controller;

import annotation.RequestMapping;
import db.Database;
import servlet.HttpRequest;
import servlet.HttpResponse;
import model.User;
import service.UserService;
import session.SessionManager;
import type.RequestMethod;
import view.ModelAndView;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static webserver.RequestHandler.logger;

@RequestMapping(path = "/user")
public class UserController implements Controller {

    private final UserService userService = new UserService();
    private SessionManager sessionManager;

    public UserController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(HttpRequest httpRequest) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpRequest httpRequest, HttpResponse httpResponse,
                        ModelAndView modelAndView) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        Optional<User> user = userService.login(userId, password);

        if (user.isEmpty()) {
            return "redirect:/user/login_failed.html";
        }
        sessionManager.createSession(user.get(),httpResponse);
        modelAndView.addModel("user", user.get());
        return "redirect:/user";
    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(HttpRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(ModelAndView modelAndView) {

        Collection<User> users = Database.findAll();
        modelAndView.addModel("users",users);

        return "/user/list.html";
    }

    @RequestMapping
    public String show() {
        return "/index.html";
    }

    @RequestMapping(path = "/login.html")
    public String showLogin() {
        return "/user/login.html";
    }

    @RequestMapping(path = "/login_failed.html")
    public String showLoginFailed() {
        return "/user/login_failed.html";
    }

    @RequestMapping(path = "/form.html")
    public String showForm() {
        return "/user/form.html";
    }

}
