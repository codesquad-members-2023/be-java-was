package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.*;

public class HttpRequestTest {

    private HttpRequest httpRequest;

    @BeforeEach
    void init() throws IOException {
        String REQUEST_HEADER_EXAMPLE = "GET /user/create?userId=core&password=123&name=kim&email=core%40naver.com HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: en-US,en;q=0.5\n" +
                "\r\n";
        httpRequest = new HttpRequest(new BufferedReader(new StringReader(REQUEST_HEADER_EXAMPLE)));
    }

    @Test
    @DisplayName("request header에 원하는 헤더 이름을 넣으면 올바른 헤더 값을 반환해야 함")
    public void get_header_value() {
        assertThat(httpRequest.getHeaderValue("Host")).isEqualTo("www.example.com");
        assertThat(httpRequest.getHeaderValue("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeaderValue("Cache-Control")).isEqualTo("max-age=0");
    }
}
