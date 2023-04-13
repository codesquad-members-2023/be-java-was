package controller;

import java.util.Map;

import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import model.User;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;

@RequestMapping(url = "/users/login")
public class UserLoginController extends Controller{

    @MethodType(value = "POST")
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest.getBody());
        String userInputPassword = params.get("password");

        if (Database.findUserById(params.get("userId")).get().validate(userInputPassword)) {
            return "redirect:/";
        }
        return "/user/login_failed.html";
    }
}
