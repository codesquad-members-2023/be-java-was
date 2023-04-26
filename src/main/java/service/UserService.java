package service;

import db.UserDatabase;
import model.User;
import util.MyObjectMapper;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    private UserDatabase userDatabase;
    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void join(Object requestParams) throws Exception {
        User user = MyObjectMapper.readValue(requestParams, User.class)
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 파라미터입니다."));

        userDatabase.join(user);
    }

    public Optional<User> login(String userId, String password) {
        User user = userDatabase.findUserById(userId);
        return Optional.ofNullable(user).filter(u -> u.getPassword().equals(password));
    }

    public Collection<User> findAll() {
        return userDatabase.findAll();
    }

}
