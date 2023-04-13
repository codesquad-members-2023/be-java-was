package service;

import db.Database;
import dto.user.UserLoginDTO;
import model.User;

public class UserLoginService {

    public User login(String loginInformation) {
        UserLoginDTO loginUser = (UserLoginDTO) ConstructorMapper.makeConstructor(loginInformation, UserLoginDTO.class)
                .orElseThrow(IllegalArgumentException::new);

        // TODO : null 로 반환하는 것이 아닌, 예외처리 필요
        // 비밀번호 조회 후 같으면 유저를 반환, 아닐 경우 null을 반환
        return Database.findUserById(loginUser.getUserId())
                .filter(m -> m.getPassword().equals(loginUser.getPassword()))
                .orElse(null);
    }
}
