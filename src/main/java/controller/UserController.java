package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserJoinService;
import util.HttpRequest;
import util.HttpResponse;

public class UserController {
    private final String HTTP_GET = "GET";
    private final String JOIN_FORM = "/user/form.html";
    private final String CREATE_USER_URL = "/user/create";
    private UserJoinService userJoinService;
    private Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    // TODO : 에러페이지 생성, 회원가입 검증
    public String process(HttpRequest request, HttpResponse response) {
        // GET 요청인 경우 분리
        if (request.getMethod().equals(HTTP_GET)) {
            // 회원 가입 폼 보여주기
            if (request.getUrl().equals(JOIN_FORM)) {
                return "/user/form.html";
            }
            // 회원가입일 경우
            if (request.getUrl().equals(CREATE_USER_URL)) {
                return addUser(request.getQueryString(), response);
            }
        }

        return "/error";
    }

    private String addUser(String queryParameters, HttpResponse response) {
        userJoinService.addUser(queryParameters);

        response.setStatus(302);
        response.setHeader("Location", "/index.html");
        return response.getResponse();
    }

}
