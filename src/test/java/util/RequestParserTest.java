package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("getPath : 요청 첫번째 줄에 입력된 값이," +
            "'/'로 시작하지 않으면 " +
            "예외가 발생한다.")
    void getPathExceptionTest() throws Exception{
        //given
        sampleRequest = "GET HTTP/1.1";

        //when
        //then
        assertThatThrownBy(() -> requestParser.getPath(sampleRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 경로입니다.");
    }

}
