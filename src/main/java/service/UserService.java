package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 회원가입 메서드
     * @param parameter 회원가입 폼 입력값. userId, password, name, email
     * @throws IllegalArgumentException 빈 값이 있을 경우
     * @throws IllegalStateException 중복된 다른 아이디가 있을 경우
     */
    public void join(Map<String, String> parameter) throws IllegalArgumentException, IllegalStateException{
        String userId = parameter.get("userId");
        String password = parameter.get("password");
        String name = parameter.get("name");
        String email = parameter.get("email");

        if (userId==null || password==null || name==null || email==null) {
            //TODO exception 처리로 바꾸기
            throw new IllegalArgumentException("입력값을 모두 처리해 주세요.");
        }
        if (Database.findUserById(userId)!=null) {
            throw new IllegalStateException("중복된 아이디가 있습니다.");
        }
        User user = new User(userId, password, name, email);

        Database.addUser(user);

        logger.info("[WELCOME] NEW USER = {}", user);
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

        logger.info("[LOGIN SUCCESS!!] userId = {}, password = {}", userId, password);
        return user;
    }
}
