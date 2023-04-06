package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserJoinService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int VALUE = 1;

    public void addUser(String userInformation) {
        String[] allInformation = userInformation.split("&");

        String[] userId = allInformation[0].split("=");
        String[] password = allInformation[1].split("=");
        String[] name = allInformation[2].split("=");
        String[] email = allInformation[3].split("=");

        User user = new User(parseCode(userId), parseCode(password), parseCode(name), parseCode(email));
        log.info(user.toString());
        Database.addUser(user);
    }

    private String parseCode(String[] userInformation) {
        return URLDecoder.decode(userInformation[1], StandardCharsets.UTF_8);
    }
}
