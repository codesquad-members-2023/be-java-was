package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParserTest {

    @Test
    @DisplayName("Http Header의 request line을 받았을 때 길이가 3인 문자열 배열을 반환한다.")
    void testSeparateUrlsCheckReturnSize() {
        // given
        String requestLine = "GET /create HttpVersion";

        // when
        String[] parsedUrl = RequestParser.separateUrls(requestLine);

        // then
        Assertions.assertThat(parsedUrl.length).isEqualTo(3);
    }

    @Test
    @DisplayName("Http Header의 request line을 받았을 때 Http method, 리소스 url, http 버전이 잘 분리되는지 확인한다.")
    void testSeparateUrlsCheckMapping() {
        // given
        String requestLine = "GET /create HttpVersion";

        // when
        String[] parsedUrl = RequestParser.separateUrls(requestLine);
        String[] expected = {"GET", "/create", "HttpVersion"};

        // then
        Assertions.assertThat(parsedUrl).containsExactly(expected);
    }
}
