package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class HttpRequestTest {

    String line = "GET /index.html HTTP/1.1";
    HttpRequest httpRequest = new HttpRequest(line);

    @Test
    @DisplayName("요청받은 메소드를 확인한다")
    void getMethod() {
        String method = httpRequest.getMethod();
        assertThat(method).isEqualTo("GET");
    }

    @Test
    @DisplayName("RequestLine에서 경로를 찾아서 반환해준다")
    void getUrl() {
        // given

        // when
        String path = httpRequest.getUrl();
        // then
        assertThat(path).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("회원가입 할 때 입력한 값이 Map에 저장되어서 키 값으로 벨류값을 가져올 수 있다")
    void parseQueryString() {
        // given
        String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        Map<String, String> testMap = httpRequest.addParam(request);

        // then
        assertThat(testMap.get("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("회원가입 쿼리가 들어왔을 때 맵에 모든 요소가 잘 저장이 되었는지 확인")
    void querySize() {
        String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1";

        Map<String, String> testMap = httpRequest.addParam(request);

        assertThat(testMap.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("입력받은 body를 \":\" 기준으로 분리되어 저장되었는지 확인")
    void getHeaders() {
        String body = "Content-Type: application/x-www-form-urlencoded";
        Map<String, String> testMap = httpRequest.addHeader(body);
        assertThat(testMap.get("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
    }

    @Test
    @DisplayName("입력받은 body의 사이즈 확인")
    void getHeadersSize() {
        String body = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*";
        Map<String, String> testMap = httpRequest.addHeader(body);
        assertThat(testMap.size()).isEqualTo(5);
    }
}
