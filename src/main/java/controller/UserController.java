package controller;

import java.util.Map;

import model.User;
import util.HttpRequest;

public class UserController {

    public String requestMapping(HttpRequest httpRequest) {
        if (httpRequest.getUrl().equals("/")) {
            httpRequest.setUrl("/index.html");
            return httpRequest.getUrl();
        }

        if (httpRequest.getUrl().equals("/user/create")) {
            return userJoin(httpRequest);
        }

        return httpRequest.getUrl();
    }

    private String userJoin(HttpRequest httpRequest) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));

        return "redirect:/";
    }
}
