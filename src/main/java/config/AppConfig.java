package config;

import controller.URLController;
import controller.UserController;
import service.UserJoinService;

public class AppConfig {

    public static UserController userController() {
        return new UserController(userJoinService());
    }

    public static UserJoinService userJoinService() {
        return new UserJoinService();
    }

    public static URLController urlController() {
        return new URLController();
    }
}
