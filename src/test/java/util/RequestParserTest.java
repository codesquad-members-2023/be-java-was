package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    RequestParser requestParser;
    String sampleRequest;

    @BeforeEach
    void init() {
        requestParser = new RequestParser();
    }

    @Test
    @DisplayName("getPath : 요청 첫번째 줄이 입력되었을 때," +
            "path를 문자열로 반환할 수 있다.")
    public void getPathTest() throws Exception{
        //given
        sampleRequest = "GET / HTTP/1.1";

        //when
        String path = requestParser.getPath(sampleRequest);

        //then
        assertThat(path).isEqualTo("/");
    }

}
