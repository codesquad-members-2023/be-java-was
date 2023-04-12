package service;

import db.Database;
import dto.user.UserLoginDTO;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.HttpResponse;

public class UserJoinService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public String addUser(String userInformation, HttpResponse response) {

        User user = (User) ConstructorMapper.makeConstructor(userInformation, User.class)
                .orElseThrow(IllegalArgumentException::new);

        Database.addUser(user);

        response.setStatus(302);
        response.setHeader("Location", "/index.html");
        return response.getResponse();
    }

    public String login(String loginInformation, HttpResponse response) {
        UserLoginDTO loginUser = (UserLoginDTO) ConstructorMapper.makeConstructor(loginInformation, UserLoginDTO.class)
                .orElseThrow(IllegalArgumentException::new);
        User findUser = Database.findUserById(loginUser.getUserId());

        if (findUser.getPassword().equals(loginUser.getPassword()) && findUser.getUserId().equals(loginUser.getUserId())) {
            response.setStatus(302);
            response.setHeader("Location", "/index.html");
            log.debug("로그인 성공");
            return response.getResponse();
        }

        response.setStatus(200);
        response.setHeader("Location", "/user/login_failed.html");
        log.debug("로그인 실패");
        return response.getResponse();
    }
}
