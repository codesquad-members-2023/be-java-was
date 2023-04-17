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

    /**
     * Home 아이콘에 비회원 Or 회원 ID를 표시해주는 기능
     * 브라우저에서 쿠키 값을 남겨두는 경우가 있어서 쿠키 값은 있지만 세션 DB의 값과는 일치하지 않는 경우가 발생해 복잡하게 예외처리했는데 리팩토링 필요..
     * @param httpRequest
     * @param httpResponse
     * @return
     */
    @MethodType(value = "GET")
    public String home(HttpRequest httpRequest, HttpResponse httpResponse) {
        Optional<String> sessionId = httpRequest.getSessionId();
        //Home 아이콘에 비회원 Or 회원 ID를 표시해주는 기능
        httpResponse.setModelAttribute("loginedUserId", "비회원");
        if (!sessionId.isEmpty()) {
            String parsedSessionId = HttpRequestUtils.parseSessionId(sessionId.get());

            User loginedUser = SessionDb.getUserBySessionId(parsedSessionId);

            //유효한 세션이 아닌 경우 쿠키를 초기화
            if (loginedUser == null) {
                //유효 세션이 아니면 요청에 보내고 있는 쿠키 무효화하기
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
