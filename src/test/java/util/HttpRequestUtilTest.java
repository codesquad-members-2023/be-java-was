package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

public class HttpRequestUtilTest {

    @Test
    @DisplayName("reqeustLine으로부터 method를 읽어 GET을 리턴한다.")
    void parseHttpMethod() {
        String requestLine = "GET /index.html HTTP/1.1";

        HttpRequest request = new HttpRequest();
        request.setRequestLine(requestLine);

        String method = request.getMethod();

        Assertions.assertThat(method).isEqualTo("GET");
    }


    @Test
    @DisplayName("url 분리해서 index.html을 리턴한다.")
    void parseUrl() {
        String requestLine = "GET /index.html HTTP/1.1";

        HttpRequest request = new HttpRequest();
        request.setRequestLine(requestLine);

        String url = request.getUrl();

        Assertions.assertThat(url).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("회원가입을 해서 쿼리 파라미터가 있을 떄, /user/create 를 올바르게 리턴한다.")
    void parseUrlWithQueryParam() {
        String requestLine = "GET /user/create?userId=first&password=password%21&name=123&email=123%40123 HTTP/1.1";

        HttpRequest request = new HttpRequest();
        request.setRequestLine(requestLine);

        String url = request.getUrl();

        Assertions.assertThat(url).isEqualTo("/user/create");
    }
}
