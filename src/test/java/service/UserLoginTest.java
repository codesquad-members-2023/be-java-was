package service;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserLoginTest {

    UserLoginService loginService = new UserLoginService();

    @BeforeEach
    void beforeEach() {
        User user = new User("test", "test", "testName", "test@email.com");
        Database.addUser(user);
    }

    @AfterEach
    void afterEach() {
        Database.clear();
    }

    @Test
    @DisplayName("회원가입된 유저의 경우 정상적인 로그인 동작")
    void loginTest() {
        // given
        String queryString = "userId=test&password=test";

        // when
        User login = loginService.login(queryString);

        // then
        Assertions.assertThat(login.getUserId()).isEqualTo("test");
    }

    @Test
    @DisplayName("회원가입되지 않은 유저의 경우 정상적인 로그인이 작동하지 않는다.")
    void loginFailUserId() {
        String queryString = "userId=testerror&password=test";

        // when
        User login = loginService.login(queryString);

        // then
        Assertions.assertThat(login).isEqualTo(null);
    }

    @Test
    @DisplayName("비밀번호가 틀린 경우 로그인이 작동하지 않는다.")
    void loginFailPassword() {
        String queryString = "userId=test&password=testerror";

        // when
        User login = loginService.login(queryString);

        // then
        Assertions.assertThat(login).isEqualTo(null);
    }
}
