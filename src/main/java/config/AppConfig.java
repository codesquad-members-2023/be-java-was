package config;

import controller.Controller;
import controller.UserController;
import controller.ViewController;

public class AppConfig {

    public static Controller getUserController() {
        return new UserController();
    }

    public static Controller getViewController() {
        return new ViewController();
    }

}
