package controller;

import db.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.Method;
import service.UserService;

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
    void init() {
        httpRequestJoin = mock(HttpRequest.class);
        httpRequestLogin = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);

        given(httpRequestJoin.getMethod()).willReturn(Method.POST);
        given(httpRequestJoin.isPath("/user/create")).willReturn(true);
        given(httpRequestJoin.getBody()).willReturn("userId=member&password=1234&name=이린&email=iirin@naver.com");
        given(httpResponse.redirect("/")).willCallRealMethod();

        userController = new UserController(new UserService());
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
    @DisplayName("로그인 성공시 반환 페이지 테스트")
    public void whenLoginFail() throws Exception{
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

}
