package controller;

import service.UserJoinService;

public class UserController {
    private final String HTTP_GET = "GET";
    private final String JOIN_FORM = "/user/form.html";
    private final String CREATE_USER_URL = "/user/create";
    UserJoinService userJoinService;

    public UserController(UserJoinService userJoinService) {
        this.userJoinService = userJoinService;
    }

    // TODO : 에러페이지 생성, 회원가입 검증
    public String process(String httpMethod, String path, String uriPath) {
        // GET 요청인 경우 분리
        if (httpMethod.equals(HTTP_GET)) {
            // 회원 가입 폼 보여주기
            if (path.equals(JOIN_FORM)) {
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

    // 쿼리 파라미터 받아서 userInformation 반환
    private String getQueryParameters(String uriPath) {
        String[] split = uriPath.split("\\?");
        return split[1];
    }

    private String showUserJoinForm() {
        return "/user/form.html";
    }
}
