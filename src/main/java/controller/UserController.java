package controller;

import service.UserJoinService;

public class UserController {
    private final String HTTP_GET = "GET";
    private final String CREATE_USER_URL = "/user/create";
    UserJoinService userJoinService;

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    public String process(String httpMethod, String path, String queryParameters) {
        // GET 요청인 경우 분리
        if (httpMethod.equals(HTTP_GET)) {
            // 회원가입일 경우
            if (path.equals(CREATE_USER_URL)) {
                return addUser(queryParameters);
            }
        }

        return "/error";
    }

    private String addUser(String queryParameters) {
        userJoinService.addUser(queryParameters);
        return "/index.html";
    }
}
