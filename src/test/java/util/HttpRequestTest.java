package util;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    @DisplayName("Request 라인을 입력받아 Method, URL을 구분하여 저장한다.")
    void init() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.init("GET /user/create?userId=poro&password=123&name=pororo&email=ngw7617%40naver.com HTTP/1.1");

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.getMethod()).isEqualTo("GET");
        softAssertions.assertThat(httpRequest.getUrl()).isEqualTo("/user/create");
        softAssertions.assertThat(httpRequest.getParams())
                .containsEntry("userId","poro")
                .containsEntry("password","123")
                .containsEntry("name","pororo")
                .containsEntry("email","ngw7617%40naver.com");
        softAssertions.assertAll();
    }
}
