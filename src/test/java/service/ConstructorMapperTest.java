package service;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConstructorMapperTest {

    @Test
    @DisplayName("올바르게 입력된 queryString을 통한 User 객체 생성 테스트 ")
    void makeUserTest() {
        // given
        String queryString = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        User user = (User)ConstructorMapper.makeConstructor(queryString, User.class).orElseThrow(IllegalArgumentException::new);

        // then
        Assertions.assertThat(user.getUserId()).isEqualTo("javajigi");
    }
}
