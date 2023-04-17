package controller;

import annotation.ExceptionHandler;
import exception.UserInfoException;

import java.util.Map;
import java.util.Optional;

import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import model.User;
import session.SessionDb;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;

@RequestMapping(url = "/users/login")
public class UserLoginController extends Controller {

    @MethodType(value = "POST")
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest.getBody());
        String userInputPassword = params.get("password");
        String userId = params.get("userId");

        User user = Database.findUserById(userId).orElseThrow(() -> new UserInfoException("존재하지 않는 유저입니다."));

        if (user.validate(userInputPassword)){
            String session = SessionDb.addSessionedUser(user);
            httpResponse.addHeader("Set-cookie", String.format("sid=%s; Path=/", session));
            return "redirect:/";
        }
        throw new UserInfoException("로그인 실패");
    }

    @ExceptionHandler(exception = "UserInfoException.class")
    public String failLogin() {
        return "/user/login_failed.html";
    }
}
