package service;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    UserService userService = new UserService();


    @Test
    @DisplayName("요청 파라미터를 인자를 받으면, 데이터베이스에 회원 정보가 저장되어야 한다.")
    void joinTest() throws Exception {
        // given
        String requestParams = "userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com";


        // when
        userService.join(requestParams);
        User reultUser = Database.findUserById("cire");
        User expectUser = new User("cire", "1234", "이동준", "dltpwns6@naver.com");

        Assertions.assertThat(reultUser).usingRecursiveComparison().isEqualTo(expectUser);

    }

}
