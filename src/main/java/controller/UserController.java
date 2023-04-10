package controller;

import service.UserSignUpService;

public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    public String process(String httpMethod, String path, String uri) {
        if (httpMethod.equals("GET")) {
            if (path.equals("/user/form.html")) {
                return "/user/form.html";
            }
            if (path.equals("/user/create")) {
                String queryString = getQueryString(uri);
                return saveUser(queryString);
            }
        }

        return "/error";
    }

    private String getQueryString(String uri) {
        String[] splittedUri = uri.split("\\?");
        return splittedUri[1];
    }

    public String saveUser(String queryString) {
        userSignUpService.userSignUp(queryString);
        return "/index.html";
    }
}
