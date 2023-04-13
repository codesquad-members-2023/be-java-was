package config;

import controller.URLController;
import controller.UserController;
import service.UserJoinService;
import service.UserLoginService;

public class AppConfig {

    public static UserController userController() {
        return new UserController(userJoinService(), userLoginService());
    }

    public static UserJoinService userJoinService() {
        return new UserJoinService();
    }

    public static URLController urlController() {
        return new URLController();
    }

    public static UserLoginService userLoginService() {
        return new UserLoginService();
    }
}
