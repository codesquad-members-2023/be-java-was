package controller;

import db.Database;
import model.User;

import java.util.Collection;

public class UserController {

    public void saveUser(User user) {
        Database.addUser(user);
    }

    public Collection<User> showAllUsers() {
        return Database.findAll();
    }
}
