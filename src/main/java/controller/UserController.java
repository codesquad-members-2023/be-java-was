package controller;

import cookie.Cookie;
import cookie.CookieStore;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserJoinService;
import request.HttpRequest;
import response.HttpResponse;
import service.UserLoginService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

import static util.HttpMethod.*;
import static util.UserURL.*;

public class UserController {
    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;
    private final CookieStore cookieStore;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserJoinService userJoinService, UserLoginService userLoginService, CookieStore cookieStore) {
        this.userJoinService = userJoinService;
        this.userLoginService = userLoginService;
        this.cookieStore = cookieStore;
    }

    // TODO : 에러페이지 생성, 회원가입 검증
    public String process(HttpRequest request, HttpResponse response, BufferedReader br, Cookie cookie) throws IOException {
        // GET 요청인 경우 분리
        if (request.getMethod().equals(HTTP_GET)) {
            // 회원 가입 폼 보여주기
            if (request.getUrl().equals(JOIN_FORM)) {
                return JOIN_FORM;
            }

            // 로그인 폼 보여주기
            if (request.getUrl().equals(LOGIN_FORM)) {
                return LOGIN_FORM;
            }
        }

        if (request.getMethod().equals(HTTP_POST)) {
            // 회원가입
            if (request.getUrl().equals(CREATE_USER_URL)) {
                return saveUser(request, response, br);
            }

            // 로그인, 로그인 실패 시 실패 페이지로 이동
            if (request.getUrl().equals(LOGIN_USER)) {
                return loginUser(request, response, br, cookie);
            }
        }

        return "/error";
    }

    private String saveUser(HttpRequest request, HttpResponse response, BufferedReader br) throws IOException {
        String requestBody = request.getRequestBody(br);
        log.debug("회원가입 성공 = {}", requestBody);
        userJoinService.addUser(requestBody);
        return response.redirectHome();
    }

    private String loginUser(HttpRequest request, HttpResponse response, BufferedReader br, Cookie cookie) throws IOException {
        String requestBody = request.getRequestBody(br);
        User loginUser = userLoginService.login(requestBody);

        if (loginUser == null) {
            return response.returnToLoginFailed();
        }
        cookie.setUuid(UUID.randomUUID().toString());
        cookie.setUser(loginUser);
        response.setHeader("Set-cookie", String.format("sid=%s; Path=/", cookie.getUuid()));
        cookieStore.saveCookie(cookie);
        return response.redirectHome();
    }
}
