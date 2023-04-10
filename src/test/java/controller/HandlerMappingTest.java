package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HandlerMappingTest {
    HandlerMapping handlerMapping;

    @BeforeEach
    void init(){
        handlerMapping = new HandlerMapping();
    }

    @Test
    public void testUserController() throws Exception{
        //given
        String user_url = "/user/";

        //when
        Controller controller = handlerMapping.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(UserController.class);
    }

    @Test
    public void testViewController() throws Exception{
        //given
        String user_url = "/";

        //when
        Controller controller = handlerMapping.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(ViewController.class);
    }
}
