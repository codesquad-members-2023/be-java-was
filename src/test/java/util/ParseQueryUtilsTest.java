package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParseQueryUtilsTest {
    @Test
    @DisplayName("ParseQueryUtils.parseQueryString() 메소드가 쿼리 문자열을 파싱하여 맵으로 반환하는지 확인하는 테스트")
    public void parseParams() throws Exception {
        // given
        String line = "userId=test&password=1234&name=%EC%95%84%ED%97%B9%ED%97%B9&email=1111%40gmail.com";

        Map<String, String> expectedParams = Map.of(
                "userId", "test",
                "password", "1234",
                "name", "%EC%95%84%ED%97%B9%ED%97%B9",
                "email", "1111%40gmail.com"
        );

        // when
        Map<String, String> params = ParseQueryUtils.parseQueryString(line);

        // then
        Assertions.assertThat(params).isEqualTo(expectedParams);
    }

    @Test
    @DisplayName("ParseQueryUtils.parseQueryString() 메소드가 빈 문자열을 파싱하여 빈 맵으로 반환하는지 확인하는 테스트")
    public void parseEmptyParams() throws Exception {
        // given
        String line = "";
        Map<String, String> emptyMap = new HashMap<>();

        // when
        Map<String, String> params = ParseQueryUtils.parseQueryString(line);

        // then
        assertThat(params).isEqualTo(emptyMap);
    }
}