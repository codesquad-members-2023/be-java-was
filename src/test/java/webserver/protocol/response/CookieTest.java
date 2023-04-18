package webserver.protocol.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    Cookie cookie;
    Logger logger = LoggerFactory.getLogger(CookieTest.class);

    @BeforeEach
    void init() {
        cookie = new Cookie("test", "testValue");
    }

    @Test
    @DisplayName("cookie toString을 하면 세팅되지 않은 값을 제외하고 스트링을 반환한다.")
    public void toStringTest() throws Exception{
        cookie.setPath("/");
        cookie.setDomain("localHost:8080");
        
        String cookieStr = cookie.toString();

        logger.debug(cookieStr);
        assertThat(cookieStr).isEqualTo("test=testValue; Domain=localHost:8080; Path=/");
    }

}
