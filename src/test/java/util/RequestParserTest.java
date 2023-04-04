package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestParserTest {

    RequestParser requestParser;
    String sampleRequest;

    @BeforeEach
    void init() {
        requestParser = new RequestParser();
    }

    @Test
    @DisplayName("getUriPath : 요청 첫번째 줄이 입력되었을 때," +
            "uripath를 문자열로 반환할 수 있다.")
    public void getUriPathTest() throws Exception{
        //given
        sampleRequest = "GET / HTTP/1.1";

        //when
        String uripath = requestParser.getUriPath(sampleRequest);

        //then
        assertThat(uripath).isEqualTo("/");
    }

    @Test
    @DisplayName("getUrl : 요청 첫번재 줄에서 path를 문자열로 반환할 수 있다.")
    public void getPathTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf%40naver HTTP/1.1";

        //when
        String path = requestParser.getPath(sampleRequest);

        //then
        assertThat(path).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("getQueryParameter : 요청 첫번재 줄에서 path를 쿼리파라미터 맵으로 반환할 수 있다.")
    public void getQueryParameterTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf@naver HTTP/1.1";

        //when
        Map<String, String> queryParammeter = requestParser.getQueryParammeter(sampleRequest);

        //then
        assertThat(queryParammeter.get("userId")).isEqualTo("member");
        assertThat(queryParammeter.get("password")).isEqualTo("1234");
        assertThat(queryParammeter.get("email")).isEqualTo("asdf@naver");
    }

}
