package controller;

import service.UserJoinService;

public class UserController {
    private final String HTTP_GET = "GET";
    private final String CREATE_USER_URL = "/user/create";
    UserJoinService userJoinService;

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    public String process(String httpMethod, String path, String uriPath) {
        // GET 요청인 경우 분리
        if (httpMethod.equals(HTTP_GET)) {
            // 회원 가입 폼 보여주기
            if (path.equals("/user/form.html")) {
                return showUserJoinForm();
            }
            // 회원가입일 경우
            if (path.equals(CREATE_USER_URL)) {
                String queryParameters = getQueryParameters(uriPath);
                return addUser(queryParameters);
            }
        }

        return "/error";
    }

    private String addUser(String queryParameters) {
        userJoinService.addUser(queryParameters);
        return "/index.html";
    }

    private String getQueryParameters(String uriPath) {
        String[] split = uriPath.split("\\?");
        return split[1];
    }

    private String showUserJoinForm() {
        return "/user/form.html";
    }
}
