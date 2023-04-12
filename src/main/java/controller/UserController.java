package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserJoinService;
import request.HttpRequest;
import response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class UserController {
    private final String HTTP_GET = "GET";
    private final String HTTP_POST = "POST";
    private final String JOIN_FORM = "/user/form.html";
    private final String CREATE_USER_URL = "/user/create";
    private final String LOGIN_FORM = "/user/login.html";
    private final String LOGIN_USER = "/user/login";
    private final UserJoinService userJoinService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    // TODO : 에러페이지 생성, 회원가입 검증
    public String process(HttpRequest request, HttpResponse response, BufferedReader br) throws IOException {
        log.info("user controller 내부에서의 url = {}", request.getUrl());
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
                String requestBody = request.getRequestBody(br);
                log.debug("회원가입 성공 = {}", requestBody);
                return userJoinService.addUser(requestBody, response);
            }

            // 로그인
            if (request.getUrl().equals(LOGIN_USER)) {
                String requestBody = request.getRequestBody(br);
                return userJoinService.login(requestBody, response);
            }
        }

        return "/error";
    }
}
