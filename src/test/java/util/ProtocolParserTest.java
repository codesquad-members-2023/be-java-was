package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.protocol.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolParserTest {

    ProtocolParser requestParser;
    String sampleRequest;

    @BeforeEach
    void init() {
        requestParser = new ProtocolParser();
    }

    @Test
    @DisplayName("getUriPath : 요청 첫번째 줄이 입력되었을 때," +
            "uripath를 문자열로 반환할 수 있다.")
    void getUriPathTest() throws Exception{
        //given
        sampleRequest = "GET / HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        assertThat(httpRequest.getPath()).isEqualTo("/");
    }

    @Test
    @DisplayName("getUrl : 요청 첫번재 줄에서 path를 문자열로 반환할 수 있다.")
    void getPathTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf%40naver HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        assertThat(httpRequest.getPath()).isEqualTo("/model/create");
    }

    @Test
    @DisplayName("getQueryParameter : 요청 첫번재 줄에서 path를 쿼리파라미터 맵으로 반환할 수 있다.")
    public void getQueryParameterTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf@naver HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        assertThat(httpRequest.getParameter("userId")).isEqualTo("member");
        assertThat(httpRequest.getParameter("password")).isEqualTo("1234");
        assertThat(httpRequest.getParameter("email")).isEqualTo("asdf@naver");
    }

}
