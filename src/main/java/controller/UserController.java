package controller;

import db.Database;
import model.User;
import service.UserSignUpService;

import java.util.Collection;

public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    public void saveUser(String queryString) {
        userSignUpService.userSignUp(queryString);
    }

    public Collection<User> showAllUsers() {
        return Database.findAll();
    }
}
