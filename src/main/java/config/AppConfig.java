package config;

import controller.URLController;
import controller.UserController;
import cookie.CookieStore;
import service.UserJoinService;
import service.UserLoginService;

public class AppConfig {

    public static UserController userController() {
        return new UserController(userJoinService(), userLoginService(), cookieStore());
    }

    private static UserJoinService userJoinService() {
        return new UserJoinService();
    }

    public static URLController urlController() {
        return new URLController();
    }

    private static UserLoginService userLoginService() {
        return new UserLoginService();
    }

    private static CookieStore cookieStore() {
        return new CookieStore();
    }
}
