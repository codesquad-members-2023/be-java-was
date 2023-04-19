package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {
    @Test
    @DisplayName("들어온 정보로 회원 가입이 되어야 함")
    public void join_new_user() {
        User user = UserService.join("core", "12345", "김동민", "core@naver.com");
        User expectedUser = Database.findUserById("core");
        assertThat(user.getUserId()).isEqualTo(expectedUser.getUserId());
        assertThat(user.getName()).isEqualTo(expectedUser.getName());
        assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(expectedUser.getPassword());
    }
}

