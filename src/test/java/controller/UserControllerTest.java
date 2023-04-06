package controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import request.HttpRequest;

class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setup() {
        userController = new UserController();
    }

    @Test
    @DisplayName("Root(/)나 index.html URL로 요청을 보냈을 때 index.html의 뷰 String을 반환한다.")
    void requestMappingRootTest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.init("GET / HTTP/1.1");

        assertThat(userController.requestMapping(httpRequest)).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("회원 가입 요청 시 유저 객체를 생성하고 Root로 리다이렉트를 반환한다.")
    void userJoin() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.init("GET /user/create?userId=poro&password=123&name=pororo&email=ngw7617%40naver.com HTTP/1.1");

        //TODO : 유저를 저장하지 않으므로 유저에 대한 테스트가 없음.
        assertThat(userController.requestMapping(httpRequest)).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("다른 URL로 접근해도 정적 뷰 String을 반환한다.")
    void requestMappingTest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.init("GET /user/login.html HTTP/1.1");

        assertThat(userController.requestMapping(httpRequest)).isEqualTo("/user/login.html");
    }
}
