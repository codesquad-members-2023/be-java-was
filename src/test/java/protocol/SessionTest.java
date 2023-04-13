package protocol;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.Session;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {
    private Logger logger = LoggerFactory.getLogger(SessionTest.class);
    private Session session;

    @BeforeEach
    void init() {
        session = new Session(new User("member", "1234", "user", "iirin@naver.com"));
    }

    @Test
    @DisplayName("랜덤 생성된 session id 는 32글자 이상이어야 한다.")
    void generateSessionIdTest() {
        session = new Session(new User("member", "1234", "user", "iirin@naver.com"));
        logger.info("session id = {}", session.getId());
        assertThat(session.getId()).hasSize(32);
    }
}
