package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import request.HttpRequest;

class UrlMapperTest {

    private UrlMapper urlMapper;
    @Mock
    UserJoinController userJoinController;

    @BeforeEach
    void setup() {
        urlMapper = new UrlMapper(userJoinController);
    }

    @Test
    void requestMapping() {
    }

    @Test
    @DisplayName("Root(/)나 index.html URL로 요청을 보냈을 때 index.html의 뷰 String을 반환한다.")
    void requestMappingRootTest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.initRequestLine("GET / HTTP/1.1");

        assertThat(urlMapper.requestMapping(httpRequest)).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("다른 URL로 접근해도 정적 뷰 String을 반환한다.")
    void requestMappingTest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.initRequestLine("GET /user/login.html HTTP/1.1");

        assertThat(urlMapper.requestMapping(httpRequest)).isEqualTo("/user/login.html");
    }
}
