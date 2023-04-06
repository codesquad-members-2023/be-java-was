package util;

import controller.UserController;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SingletonContainerTest {

    @Test
    @DisplayName("UserController 객체를 여러 번 가져와도 해당 객체는 항상 동일한 객체이다.")
    void getUserController() {
        // given, when
        UserController userControllerA = UserController.getInstance();
        UserController userControllerB = UserController.getInstance();

        // then
        assertThat(userControllerA).isSameAs(userControllerB);
    }
}