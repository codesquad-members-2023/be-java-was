package config;

import controller.UserController;
import service.UserSignUpService;

public class AppConfig {

    public AppConfig() {
    }

    public UserController makeUserController() {
        return new UserController(new UserSignUpService());
    }
}
