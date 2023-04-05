package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestUtilTest {

    @Test
    @DisplayName("Http 메서드 분리 테스트")
    void parseHttpMethod() {
        String requestLine = "GET /index.html HTTP/1.1";

        HttpRequest request = new HttpRequest(requestLine);

        String method = request.getMethod();

        Assertions.assertThat(method).isEqualTo("GET");
    }


    @Test
    @DisplayName("url 분리 테스트")
    void parseUrl() {
        String requestLine = "GET /index.html HTTP/1.1";

        HttpRequest request = new HttpRequest(requestLine);

        String url = request.getUrl();

        Assertions.assertThat(url).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("쿼리 파라미터가 있을 때 url 분리 테스트")
    void parseUrlWithQueryParam() {
        String requestLine = "GET /user/create?userId=first&password=password%21&name=123&email=123%40123 HTTP/1.1";

        HttpRequest request = new HttpRequest(requestLine);

        String url = request.getUrl();

        Assertions.assertThat(url).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("쿼리 파라미터 분리 테스트")
    void parseQueryParam() {
        String requestLine = "GET /user/create?userId=first&password=password%21&name=123&email=123%40123 HTTP/1.1";

        HttpRequest request = new HttpRequest(requestLine);

        String url = request.getQueryString();

        Assertions.assertThat(url).isEqualTo("userId=first&password=password%21&name=123&email=123%40123");
    }
}
