package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class HttpRequestTest {

    HttpRequest httpRequest = new HttpRequest();

    @Test
    @DisplayName("RequestLine에서 경로를 찾아서 반환해준다")
    void getUrl() {
        // given
        String line = "GET /index.html HTTP/1.1";
        // when
        String path = httpRequest.getUrl(line);
        // then
        assertThat(path).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("요청받은 메소드를 확인한다")
    void getMethod(){
        String line = "GET /index.html HTTP/1.1";

        String method = httpRequest.getMethod(line);
        assertThat(method).isEqualTo("GET");
    }

    @Test
    @DisplayName("회원가입 할 때 입력한 값이 Map에 저장되어서 키 값으로 벨류값을 가져올 수 있다")
    void parseQueryString() {
        // given
        String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        Map<String, String> testMap = httpRequest.parseQueryString(request);

        // then
        assertThat(testMap.get("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("회원가입 쿼리가 들어왔을 때 맵에 모든 요소가 잘 저장이 되었는지 확인")
    void querySize(){
        String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1";

        Map<String, String> testMap = httpRequest.parseQueryString(request);

        assertThat(testMap.size()).isEqualTo(3);

    }
}
