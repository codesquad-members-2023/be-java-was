package controller;

import db.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.Method;
import webserver.protocol.StatusCode;
import service.UserService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class UserControllerTest {
    @Mock
    HttpRequest httpRequestJoin;
    @Mock
    HttpRequest httpRequestLogin;
    @Mock
    HttpResponse httpResponse;

    UserController userController;

    @BeforeEach
    void init() throws IOException {
        httpRequestJoin = mock(HttpRequest.class);
        httpRequestLogin = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);

        given(httpRequestJoin.getMethod()).willReturn(Method.POST);
        given(httpRequestJoin.isPath("/user/create")).willReturn(true);
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234&name=이린&email=iirin@naver.com");

        given(httpResponse.redirect(isA(String.class))).willReturn(httpResponse);
        given(httpResponse.setCookie(isA(String.class), isA(String.class))).willReturn(httpResponse);
        given(httpResponse.forward(isA(StatusCode.class), isA(String.class))).willReturn(httpResponse);
        doNothing().when(httpResponse).response();

        userController = new UserController(new UserService());
    }

    @AfterEach
    void reset() {
        Database.clear();
    }

    @Test
    @DisplayName("회원가입을 완료하면 redirect:/ String을 반환한다.")
    void joinTest() throws Exception{
        String returnPage = userController.doPost(httpRequestJoin, httpResponse);
        assertThat(returnPage).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("회원 가입 후, 데이터 베이스에서 회원 아이디로 회원을 찾을 수 있다.")
    void joinAndGetUserIdTest() throws Exception{
        userController.doPost(httpRequestJoin, httpResponse);
        assertThat(Database.findUserById("member")).isNotNull();
    }

    @Test
    @DisplayName("회원 가입 후, 데이터베이스 findAll 시 사이즈가 1 늘어나있다.")
    void joinAndGetSizeTest() throws Exception{
        given(httpRequestJoin.getBody()).willReturn("userId=Othermember&password=1234&name=이린&email=iirin@naver.com");

        int beforeSize = Database.findAll().size();
        userController.doPost(httpRequestJoin, httpResponse);
        assertThat(Database.findAll()).hasSize(beforeSize + 1);
    }

    @Test
    @DisplayName("회원 가입 시, 폼데이터가 없는 경우 반환페이지")
    void joinEmptyFormTest() throws Exception{
        given(httpRequestJoin.getBody()).willReturn("userId=&password=1234&name=이린&email=iirin@naver.com");
        assertThat(userController.doPost(httpRequestJoin, httpResponse)).isEqualTo("/user/form_failed_empty.html");
    }

    @Test
    @DisplayName("회원 가입 시, 유저 아이디가 중복되는 경우 반환페이지")
    void joinDuplicateUserIdFormTest() throws Exception{
        userController.doPost(httpRequestJoin, httpResponse);   // 첫번째 회원가입
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234111&name=이린2&email=iirin2@naver.com");

        assertThat(userController.doPost(httpRequestJoin, httpResponse)).isEqualTo("/user/form_failed_duplicateUserId.html");
    }

    @Test
    @DisplayName("로그인 성공시 반환 페이지 테스트")
    void loginTest() throws Exception{
        //given
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234&name=이린&email=iirin@naver.com");
        userController.doPost(httpRequestJoin, httpResponse);   // 회원가입한 상태

        //when
        given(httpRequestLogin.getMethod()).willReturn(Method.POST);
        given(httpRequestLogin.isPath("/user/login")).willReturn(true);
        given(httpRequestLogin.getBody()).willReturn("userId=member&password=1234");

        //then
        String returnPage = userController.doPost(httpRequestLogin, httpResponse);
        assertThat(returnPage).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("비밀번호가 달라 로그인이 실패했을 때, 반환 페이지 테스트")
    void whenLoginFail() throws Exception{
        //given
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234&name=이린&email=iirin@naver.com");
        userController.doPost(httpRequestJoin, httpResponse);   // 회원가입한 상태

        //when
        given(httpRequestLogin.getMethod()).willReturn(Method.POST);
        given(httpRequestLogin.isPath("/user/login")).willReturn(true);
        given(httpRequestLogin.getBody()).willReturn("userId=member&password=1111");

        //then
        String returnPage = userController.doPost(httpRequestLogin, httpResponse);
        assertThat(returnPage).isEqualTo("/user/login_failed.html");
    }

    @Test
    @DisplayName("아이디가 DB에 없어 로그인이 실패했을 때, 반환 페이지 테스트")
    void whenLoginFailCauseUserId() throws Exception{
        //given
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234&name=이린&email=iirin@naver.com");
        userController.doPost(httpRequestJoin, httpResponse);   // 회원가입한 상태

        //when
        given(httpRequestLogin.getMethod()).willReturn(Method.POST);
        given(httpRequestLogin.isPath("/user/login")).willReturn(true);
        given(httpRequestLogin.getBody()).willReturn("userId=member2&password=1234");

        //then
        String returnPage = userController.doPost(httpRequestLogin, httpResponse);
        assertThat(returnPage).isEqualTo("/user/login_failed.html");
    }

}
