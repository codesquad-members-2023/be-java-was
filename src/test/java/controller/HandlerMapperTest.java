package controller;

import controller.user.UserJoinController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerMapperTest {
    HandlerMapper handlerMapper;

    @BeforeEach
    void init(){
        handlerMapper = new HandlerMapper();
    }

    @Test
    @DisplayName("/user 로 시작하는 url을 받으면 UserController를 반환받아야 한다.")
    public void testUserController() throws Exception{
        //given
        String user_url = "/user/create";

        //when
        Controller controller = handlerMapper.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(UserJoinController.class);
    }

    @Test
    @DisplayName("/ 로 시작하는 url을 받으면 DefaultController를 반환받아야 한다.")
    public void testViewController() throws Exception{
        //given
        String user_url = "/";

        //when
        Controller controller = handlerMapper.getController(user_url);

        //then
        assertThat(controller.getClass()).isEqualTo(DefaultController.class);
    }

}
