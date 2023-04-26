package service;

import db.ArticleDatabase;
import db.Database;
import db.UserDatabase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    UserDatabase userDatabase;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userDatabase = new UserDatabase(URL, USERNAME, PASSWORD);
        userService = new UserService(userDatabase);
    }

    @Test
    @DisplayName("요청 파라미터를 인자를 받으면, 데이터베이스에 회원 정보가 저장되어야 한다.")
    void joinTest() throws Exception {
        // given
        String requestParams = "userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com";


        // when
        userService.join(requestParams);
        User reultUser = userDatabase.findUserById("cire");
        User expectUser = new User("cire", "1234", "이동준", "dltpwns6@naver.com");

        Assertions.assertThat(reultUser).usingRecursiveComparison().isEqualTo(expectUser);

    }

}
