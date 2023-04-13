package util;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectMapperTest {
    private Logger logger = LoggerFactory.getLogger(ObjectMapperTest.class);
    ObjectMapper objectMapper;
    Map<String, String> joinParams;

    @BeforeEach
    void init() {
        joinParams = Map.of("userId", "member", "password", "1234", "name", "이린", "email", "iirin@naver.com");
    }

    @Test
    @DisplayName("objectMapper로 회원 객체를 생성할 수 있다.")
    public void fieldParseTest() throws Exception{
        //given
        objectMapper = new ObjectMapper(User.class, joinParams);
        //when
        User user = (User) objectMapper.mapObject();
        //then
        assertThat(user.getUserId()).isEqualTo("member");
        logger.info(user.toString());
    }
    
}
