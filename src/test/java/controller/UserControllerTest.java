package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import webserver.protocol.HttpRequest;
import webserver.protocol.Method;

import javax.annotation.meta.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @Mock
    HttpRequest httpRequest;

    @BeforeEach
    void init() {
    }

    @Test
    void mockTest() throws Exception{
        //given
        httpRequest = mock(HttpRequest.class);

        //when
        when(httpRequest.getMethod()).thenReturn(Method.GET);

        //then
        assertThat(httpRequest.getMethod()).isEqualTo(Method.GET);
    }

}
