package controller;

import model.User;
import service.UserService;
import session.Session;
import session.SessionStore;
import util.ProtocolParser;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.StatusCode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static controller.HandlerMapping.USER_URL;

public class UserController extends FrontController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward(StatusCode.OK, httpRequest.getPath()).response();
        return httpRequest.getPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String returnPage;
        if (httpRequest.isPath(USER_URL + "/create")) {
            returnPage = join(httpRequest, httpResponse);
            httpResponse.redirect("/")
                    .response();
            return returnPage;
        }
        
        if (httpRequest.isPath(USER_URL + "/login")) {
            returnPage = login(httpRequest, httpResponse);
            httpResponse.redirect("/")            // 완료 후 index로 리다이렉트
                    .response();
            return returnPage;
        }
        return httpRequest.getPath();
    }

    private String login(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
            User loginedUser = userService.login(parameter);

            Session loginSession = new Session(loginedUser.getUserId());
            SessionStore.addSession(loginSession);

            httpResponse.setCookie("SID", loginSession.getId())   // SID cookie 세팅 // 만료가 없으면 session 쿠키가 된다.
                    .setCookie("Path", "/");         // 유효 범위

            return "redirect:/";
        } catch (IllegalArgumentException e) {
            // TODO modelAndview 만들어서 리팩토링하기
            httpResponse.forward(StatusCode.UNAUTHORIZED, "/user/login_failed.html").response();
            return "/user/login_failed.html";
        }
    }

    private String join(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
            userService.join(parameter);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            // TODO modelAndview 만들어서 리팩토링하기
            httpResponse.forward(StatusCode.BAD_REQUEST, "/user/form_failed_empty.html")
                    .response();
            return "/user/form_failed_empty.html";
        } catch (IllegalStateException e) {
            // TODO modelAndview 만들어서 리팩토링하기
            httpResponse.forward(StatusCode.BAD_REQUEST, "/user/form_failed_duplicateUserId.html")
                    .response();
            return "/user/form_failed_duplicateUserId.html";
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }


}
