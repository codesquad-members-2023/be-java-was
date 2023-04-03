package service;

import db.Database;
import model.User;

import java.util.Map;

public class UserService {
    public User join(Map<String, String> param) {
        User newMember = new User(param.get("userId"), param.get("password"), param.get("name"), param.get("email"));
        Database.addUser(newMember);
        return Database.findUserById(param.get("userId"));
    }
}
