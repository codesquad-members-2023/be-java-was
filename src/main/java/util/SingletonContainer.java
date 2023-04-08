package util;

import controller.UserController;

public class SingletonContainer {
    private UserController userController;

    public SingletonContainer() {
        this.userController = UserController.getInstance();
    }

    public static UserController getUserController() {
        return UserController.getInstance();
    }
}
