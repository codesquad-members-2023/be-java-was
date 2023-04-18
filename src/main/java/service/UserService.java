package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static webserver.RequestHandler.logger;

public class UserService {
    /**
     * 회원가입 메서드
     * @param parameter 회원가입 폼 입력값. userId, password, name, email
     * @throws IllegalArgumentException 빈 값이 있을 경우
     * @throws IllegalStateException 중복된 다른 아이디가 있을 경우
     */
    public void join(Map<String, String> parameter) throws IllegalArgumentException, IllegalStateException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String userId = parameter.get("userId");

        if (Database.findUserById(userId)!=null) {
            throw new IllegalStateException("중복된 아이디가 있습니다.");
        }

        User user = (User) new ObjectMapper(parameter, User.class).mapObject();
        Database.addUser(user);

        logger.debug("[WELCOME] NEW USER = {}", user);
    }

    /**
     * 로그인 메서드
     * @param parameter 로그인 폼 입력값 userId, password
     * @return 로그인한 user
     * @throws IllegalArgumentException 아이디가 DB에 없거나 비밀번호가 잘못 되었을 때
     */
    public User login(Map<String, String> parameter) throws IllegalArgumentException{
        String userId = parameter.get("userId");
        String password = parameter.get("password");

        User user;
        if ((user=Database.findUserById(userId))==null || !user.isLogined(password)) {
            throw new IllegalArgumentException("아이디가 없거나 비밀번호가 잘못되었습니다.");
        }

        logger.debug("[LOGIN SUCCESS!!] userId = {}, password = {}", userId, password);
        return user;
    }
}
