package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class UserSignUpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String userId, password, name, email;

    public void userSignUp(String queryString) {
        String[] newUserInfo = queryString.split("&");
        logger.debug("newUserInfo: {}", Arrays.toString(newUserInfo));
        for (int i = 0; i < newUserInfo.length; i++) {
            String[] splittedUserInfo = newUserInfo[i].split("=");
            if (i == 0) {
                userId = splittedUserInfo[1];
                continue;
            }
            if (i == 1) {
                password = splittedUserInfo[1];
                continue;
            }
            if (i == 2) {
                name = splittedUserInfo[1];
                continue;
            }
            if (i == 3) {
                email = splittedUserInfo[1];
            }
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);
        logger.debug("Added User: {}", user);
    }
}
