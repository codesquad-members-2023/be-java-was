package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerMappingTest {
    HandlerMapping handlerMapping;

    @BeforeEach
    void init(){
        handlerMapping = new HandlerMapping();
    }

    @Test
    @DisplayName("/user 로 시작하는 url을 받으면 UserController를 반환받아야 한다.")
    public void testUserController() throws Exception{
        //given
        String user_url = "/user/create";

        //when
        Controller controller = handlerMapping.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(UserController.class);
    }

    @Test
    @DisplayName("/ 로 시작하는 url을 받으면 DefaultController를 반환받아야 한다.")
    public void testViewController() throws Exception{
        //given
        String user_url = "/";

        //when
        Controller controller = handlerMapping.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(DefaultController.class);
    }
}
