package util;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.protocol.HttpRequest;

import java.util.Map;

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
    @DisplayName("getUriPath : request line이 입력되었을 때," +
            "uripath를 문자열로 반환할 수 있다.")
    void getUriPathTest() throws Exception{
        //given
        sampleRequest = "GET / HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        assertThat(httpRequest.getPATH()).isEqualTo("/");
    }

    @Test
    @DisplayName("getUrl : request line에서 path를 문자열로 반환할 수 있다.")
    void getPathTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf%40naver HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        assertThat(httpRequest.getPATH()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("getQueryParameter : request line에서 path를 쿼리파라미터 맵으로 반환할 수 있다.")
    public void getQueryParameterTest() throws Exception{
        //given
        sampleRequest = "GET /user/create?userId=member&password=1234&name=user&email=asdf@naver HTTP/1.1";

        //when
        HttpRequest httpRequest = new HttpRequest(sampleRequest);

        //then
        SoftAssertions.assertSoftly( softAssertions -> {
            assertThat(httpRequest.getParameter("userId")).isEqualTo("member");
            assertThat(httpRequest.getParameter("password")).isEqualTo("1234");
            assertThat(httpRequest.getParameter("email")).isEqualTo("asdf@naver");

        });
    }

    @Test
    @DisplayName("parseHeader : 헤더 문자열이 주어졌을 때 키, 값으로 헤더를 파싱할 수 있다.")
    void parseHeader() {
        String example = "Host: www.example.com";
        Map<String, String> parameter = ProtocolParser.parseHeaders(example);
        assertThat(parameter.get("Host")).isEqualTo("www.example.com");
    }

    @Test
    @DisplayName("parseHeader : 헤더가 6개인 헤더 문자열이 주어졌을 때 6개의 헤더를 모두 파싱할 수 있다.")
    void parseHeaderCheckParseAll() {
        String example = "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "Accept-Language: en-US,en;q=0.9,ko;q=0.8\n" +
                "Connection: keep-alive\n" +
                "Referer: https://www.google.com/";
        Map<String, String> parameter = ProtocolParser.parseHeaders(example);
        assertThat(parameter).hasSize(6);
    }
}
