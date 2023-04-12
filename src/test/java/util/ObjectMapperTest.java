package util;

import annotation.Param;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperTest {
    private Logger logger = LoggerFactory.getLogger(ObjectMapperTest.class);
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {

    }

    @Test
    @DisplayName("클래스의 필드 정보를 파싱할 수 있다.")
    public void fieldParseTest() throws Exception{
        //given
        objectMapper = new ObjectMapper(User.class, new HashMap<>());
        //when
        Field[] fields = objectMapper.parseFields();

        for (Field f : fields) {
            logger.info("fieldName = {}", f.getName());
        }
        //then
        assertThat(fields).hasSize(4);
    }
    
}
