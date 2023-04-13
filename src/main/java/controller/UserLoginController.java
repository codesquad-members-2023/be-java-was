package controller;

import java.util.Map;

import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import session.SessionDb;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;

@RequestMapping(url = "/users/login")
public class UserLoginController extends Controller{

    @MethodType(value = "POST")
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest.getBody());
        String userInputPassword = params.get("password");
        String userId = params.get("userId");

        if (Database.findUserById(userId).get().validate(userInputPassword)) {
            String session = SessionDb.addSessionedUser(userId);
            httpResponse.addHeader("Set-cookie", String.format("sid=%s; Path=/", session));
            return "redirect:/";
        }
        return "/user/login_failed.html";
    }
}
