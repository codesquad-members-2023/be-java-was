package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserJoinService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void addUser(String userInformation) {

        User user = (User) ConstructorMapper.readValue(userInformation, User.class)
                .orElseThrow(IllegalArgumentException::new);

        Database.addUser(user);
    }
}
