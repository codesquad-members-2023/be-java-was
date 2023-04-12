package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import type.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadTest {
    StringBuilder sb;
    HttpRequest head;
    @BeforeEach
    public void beforeEach() throws IOException {
        sb = new StringBuilder();

    }
    @Test
    @DisplayName("HttpRequest 클래스의 생성자가 String 타입의 헤더를 입력받아, 요청라인을 파싱해야 한다.")
    public void HttpRequestLineTest() throws IOException {
        // when
        sb.append("GET /index.html?userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));


        // given
        head = new HttpRequest(br);

        // then
        assertThat(head.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(head.getPath()).isEqualTo("/index.html");
        assertThat(head.getQuery()).isEqualTo("userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com");
        assertThat(head.getVersion()).isEqualTo("HTTP1.1");
    }

    @Test
    @DisplayName("HttpRequest 클래스의 생성자가 String 타입의 헤더를 입력받아, 헤더를 파싱해야 한다.")
    public void HttpRequestMimeTest() throws IOException {
        // when
        sb.append("GET /index.html?userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));
        // given
        head = new HttpRequest(br);

        // then
        assertThat(head.getHeaderElement("accept")).isEqualTo("*/*");
        assertThat(head.getHeaderElement("Content-Type")).isEqualTo("text");
    }
}
