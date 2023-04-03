package controller;

import java.util.Map;

import model.User;
import util.HttpRequest;
import util.HttpRequestUtils;

public class UserController {

    public static void requestMapping(HttpRequest httpRequest) {
        if (httpRequest.getUrl().equals("/")) {
            httpRequest.setUrl("/index.html");
        }

        if (httpRequest.getUrl().equals("/user/create")) {
            userJoin(httpRequest);
        }

    }

    public static void userJoin(HttpRequest httpRequest) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));
    }
}
