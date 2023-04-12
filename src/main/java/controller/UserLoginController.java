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
public class UserLoginController implements Controller{

    @MethodType(value = "POST")
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest.getBody());

        User user = Database.findUserById(params.get("userId")).get();

        return "redirect:/";
    }
}
