package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParserTest {

    @Test
    @DisplayName("Http Header의 request line을 받았을 때 길이가 3인 문자열 배열을 반환한다.")
    void testSeparateUrlsCheckSizeOfReturnData() {
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

    @Test
    @DisplayName("쿼리 파라미터가 포함된 resource url이 입력되면 2개로 분리되는지 확인한다.")
    void testParseQueryParameterCheckSizeOfReturnData() {
        // given
        String resourceUrl = "/create?userId=testA&password=A&name=testerA&email=testA@test.com";

        // when
        String[] parsedData = RequestParser.parseQueryParameter(resourceUrl);

        // then
        Assertions.assertThat(parsedData.length).isEqualTo(2);
    }

    @Test
    @DisplayName("쿼리 파라미터가 포함된 resource url이 입력되면 경로와 데이터 단위로 분리되는지 확인한다.")
    void testParseQueryParameterCheckMapping() {
        // given
        String resourceUrl = "/create?userId=testA&password=A&name=testerA&email=testA@test.com";

        // when
        String[] parsedData = RequestParser.parseQueryParameter(resourceUrl);
        String[] expected = {"/create", "userId=testA&password=A&name=testerA&email=testA@test.com"};

        // then
        Assertions.assertThat(parsedData).containsExactly(expected);
    }

    @Test
    @DisplayName("쿼리 파라미터가 포함된 resource url이 ?를 기준으로 분리되는지 확인한다.")
    void testParseQueryParameterSplitedByQuestionMark() {
        // given
        String resourceUrl = "/create?userId=testA&password=A&name=testerA&email=testA@test.com";

        // when
        String[] parsedData = RequestParser.parseQueryParameter(resourceUrl);
        String[] expected = resourceUrl.split("\\?");

        // then
        Assertions.assertThat(parsedData).containsExactly(expected);
    }
}
