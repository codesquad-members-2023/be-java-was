package db;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class UserDatabaseTest {
    private final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    UserDatabase userDatabase;

    @BeforeEach
    public void beforeEach() {
        userDatabase = new UserDatabase(URL, USERNAME, PASSWORD);
        userDatabase.deleteAllUser();
    }

    @AfterEach
    public void afterEach() {
        userDatabase.deleteAllUser();
    }
    @Test
    @DisplayName("사용자 아이디로 사용자 데이터베이스에서 사용자 데이터를 얻어야 한다.")
    public void findTest() {
        // given
        User user = new User("UserA", "1234", "lee", "dltpwns0@naver.com");

        // when
        userDatabase.join(user);
        User userA = userDatabase.findUserById("UserA");

        // then
        assertThat(userA).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    @DisplayName("사용자 데이터베이스에서 모든 사용자 데이터를 얻어야 한다.")
    public void findAllTest() {
        // given
        User userA = new User("UserA", "1", "lee", "dltpwns0@naver.com");
        User userB = new User("UserB", "2", "dong", "dltpwns0@naver.com");
        User userC = new User("UserC", "3", "jun", "dltpwns0@naver.com");
        userDatabase.join(userA);
        userDatabase.join(userB);
        userDatabase.join(userC);
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(userA);
        expectedUsers.add(userB);
        expectedUsers.add(userC);

        // when
        List<User> actualUsers = userDatabase.findAll();

        // then
        assertThat(actualUsers).usingRecursiveComparison().isEqualTo(expectedUsers);

    }

    @Test
    @DisplayName("사용자 아이디로 사용자 데이터베이스에서 사용자 데이터를 제거해야한다.")
    public void deleteTest() {
        // given
        User user = new User("UserA", "1234", "lee", "dltpwns0@naver.com");

        // when
        userDatabase.join(user);
        int result = userDatabase.deleteUser("UserA");
        User userA = userDatabase.findUserById("UserA");

        // then
        assertThat(userA).isNull();

    }

    @Test
    @DisplayName("사용자에 대한 정보로 사용자의 닉네임과 이메일을 업데이트 해야한다.")
    public void updateTest() {
        // given
        User beforeUser = new User("UserA", "1234", "before", "before@naver.com");
        User afterUser = new User("UserA", "1234", "after", "after@naver.com");

        User expectedUser = new User("UserA", "1234", "after", "after@naver.com");

        // when
        userDatabase.join(beforeUser);
        userDatabase.update(afterUser);
        User user = userDatabase.findUserById("UserA");

        // then
        assertThat(user).usingRecursiveComparison().isEqualTo(expectedUser);
    }

}
