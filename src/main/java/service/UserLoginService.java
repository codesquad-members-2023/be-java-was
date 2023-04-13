package service;

import cookie.Cookie;
import db.Database;
import dto.user.UserLoginDTO;
import model.User;

public class UserLoginService {

    public User login(String loginInformation) {
        UserLoginDTO loginUser = (UserLoginDTO) ConstructorMapper.makeConstructor(loginInformation, UserLoginDTO.class)
                .orElseThrow(IllegalArgumentException::new);

        return Database.findUserById(loginUser.getUserId())
                .filter(m -> m.getPassword().equals(loginUser.getPassword()))
                .orElse(null);
    }
}
