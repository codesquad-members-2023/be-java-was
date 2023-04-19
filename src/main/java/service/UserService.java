package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private final static Logger log = LoggerFactory.getLogger("UserService");
    public static User join(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        log.debug("User join = {}", user);
        Database.addUser(user);
        return user;
    }
}
