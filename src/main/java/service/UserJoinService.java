package service;

import cookie.Cookie;
import db.Database;
import dto.user.UserLoginDTO;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.UUID.*;

public class UserJoinService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void addUser(String userInformation) {

        User user = (User) ConstructorMapper.makeConstructor(userInformation, User.class)
                .orElseThrow(IllegalArgumentException::new);
        Database.addUser(user);
    }
}
