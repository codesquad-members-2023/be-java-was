package config;

import controller.UserController;
import service.UserJoinService;

public class AppConfig {

    public AppConfig() {
    }

    public UserController userController() {
        return new UserController(userJoinService());
    }

    public UserJoinService userJoinService() {
        return new UserJoinService();
    }

}
