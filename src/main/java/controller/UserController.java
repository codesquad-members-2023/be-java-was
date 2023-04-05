package controller;

import model.User;

import java.util.Map;

public class UserController {
    public static void addUser(Map<String, String> params){
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
    }
}
