package util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class HttpRequestUtilsTest {

    @Test
    void parseQueryParamsTest() {

        String queryString = "userId=poro&password=123&name=pororo&email=ngw7617%40naver.com";

        assertThat(HttpRequestUtils.parseQueryParams(queryString))
                .containsEntry("userId","poro")
                .containsEntry("password","123")
                .containsEntry("name","pororo")
                .containsEntry("email","ngw7617%40naver.com");
    }
}
