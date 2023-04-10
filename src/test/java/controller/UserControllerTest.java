package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.Method;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class UserControllerTest {
    @Mock
    HttpRequest httpRequest;
    @Mock
    HttpResponse httpResponse;

    UserController userController;

    @BeforeEach
    void init() {
        httpRequest = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);

        given(httpRequest.getMethod()).willReturn(Method.POST);
        given(httpRequest.isPath("/user/create")).willReturn(true);
        given(httpRequest.getBodyParameter("userId")).willReturn("member");
        given(httpRequest.getBodyParameter("password")).willReturn("1234");
        given(httpRequest.getBodyParameter("name")).willReturn("이린");
        given(httpRequest.getBodyParameter("email")).willReturn("iirin@naver.com");

        userController = new UserController();
    }

    @Test
    @DisplayName("회원가입을 완료하면 redirect:/ String을 반환한다.")
    void joinTest() throws Exception{
        String returnPage = userController.doPost(httpRequest, httpResponse);
        assertThat(returnPage).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("회원 가입 후, 데이터 베이스에서 회원 아이디로 회원을 찾을 수 있다.")
    void joinAndGetUserIdTest() throws Exception{
        userController.doPost(httpRequest, httpResponse);
        assertThat(Database.findUserById("member")).isNotNull();
    }

    @Test
    @DisplayName("회원 가입 후, 데이터베이스 findAll 시 사이즈가 1 늘어나있다.")
    void joinAndGetSizeTest() throws Exception{
        int beforeSize = Database.findAll().size();
        userController.doPost(httpRequest, httpResponse);
        assertThat(Database.findAll()).hasSize(beforeSize + 1);
    }

}
