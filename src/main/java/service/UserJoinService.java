package service;

import db.Database;
import dto.user.UserLoginDTO;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserJoinService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void addUser(String userInformation) {

        User user = (User) ConstructorMapper.makeConstructor(userInformation, User.class)
                .orElseThrow(IllegalArgumentException::new);
        Database.addUser(user);
    }

    public boolean login(String loginInformation) {
        UserLoginDTO loginUser = (UserLoginDTO) ConstructorMapper.makeConstructor(loginInformation, UserLoginDTO.class)
                .orElseThrow(IllegalArgumentException::new);
        User findUser = Database.findUserById(loginUser.getUserId());

        if (findUser.getPassword().equals(loginUser.getPassword()) && findUser.getUserId().equals(loginUser.getUserId())) {
            // TODO : 로그인 처리 로직 추가하기
            return true;
        }
        return false;
    }
}
