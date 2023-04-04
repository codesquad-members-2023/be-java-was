package service;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserJoinTest {

    private UserJoinService userJoinService = new UserJoinService();

    @Test
    @DisplayName("회원가입 잘 되는지 테스트")
    void joinTest() {
        // given
        String userInformation = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        userJoinService.addUser(userInformation);

        // then
        User javajigi = Database.findUserById("javajigi");
        Assertions.assertThat(javajigi.getUserId()).isEqualTo("javajigi");
    }
}
