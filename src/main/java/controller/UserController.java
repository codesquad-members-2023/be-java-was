package controller;

import service.UserSignUpService;

public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    public void saveUser(String queryString) {
        userSignUpService.userSignUp(queryString);
    }
}
