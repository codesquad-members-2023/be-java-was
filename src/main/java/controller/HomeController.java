package controller;

import java.util.Optional;

import annotation.ExceptionHandler;
import annotation.MethodType;
import annotation.RequestMapping;
import db.Database;
import exception.UserInfoException;
import model.User;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;
import session.SessionDb;

@RequestMapping(url = "/")
public class HomeController extends Controller {

    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        Optional<String> sessionId = httpRequest.getSessionId();
        httpResponse.setModelAttribute("loginedUserId", "비회원");
        if (!sessionId.isEmpty()) {
            String parsedSessionId = HttpRequestUtils.parseSessionId(sessionId.get());

            User loginedUser = SessionDb.getUserBySessionId(parsedSessionId);

            // 유효한 세션이 아닌 경우 쿠키를 초기화
            if (loginedUser == null) {
                httpResponse.addHeader("Set-Cookie", String.format("sid=%s; Path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT;", parsedSessionId));
                throw new UserInfoException("세션이 만료되었습니다.");
            }
            httpResponse.setModelAttribute("loginedUserId", loginedUser.getUserId());
        }

        return "/index.html";
    }

    @ExceptionHandler(exception = "UserInfoException.class")
    public String invalidSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
