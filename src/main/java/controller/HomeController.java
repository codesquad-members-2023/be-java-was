package controller;

import java.util.Optional;

import annotation.ExceptionHandler;
import annotation.MethodType;
import annotation.RequestMapping;
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
        if (!sessionId.isEmpty()) {
            String parsedSessionId = HttpRequestUtils.parseSessionId(sessionId.get());

            User user = SessionDb.getUserBySessionId(parsedSessionId);

            // 유효한 세션이 아닌 경우 쿠키를 초기화하고(쿠키를 굳이 초기화 할 필요가 있나?) -> 쿠키 초기화 대신 다른 로직을 수행하도록.
            if (user == null) {
                httpResponse.addHeader("Set-Cookie", String.format("sid=%s; Path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT;", parsedSessionId));
                httpResponse.setModelAttribute("userId", "비회원");
                throw new UserInfoException("세션이 만료되었습니다.");
            }
            httpResponse.setModelAttribute("userId", user.getUserId());
        }

        return "/index.html";
    }

    @ExceptionHandler(exception = "UserInfoException.class")
    public String invalidSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/index.html";
    }
}
