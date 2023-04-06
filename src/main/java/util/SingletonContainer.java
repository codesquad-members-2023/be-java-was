package util;

import controller.UserController;

public class SingletonContainer {

    public static UserController getUserController() {
        return UserController.getInstance();
    }
}
