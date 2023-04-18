package controller;

import controller.user.UserJoinController;
import controller.user.UserListController;
import controller.user.UserLoginController;
import service.UserService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static String USER_JOIN = "/user/create";
    private static String USER_LIST = "/user/list";
    private static String USER_LOGIN = "/user/login";

    private static Map<String, Controller> map = Map.of(
        USER_JOIN, new UserJoinController(new UserService()),
        USER_LIST, new UserListController(new UserService()),
        USER_LOGIN, new UserLoginController(new UserService())
    );

    public static Controller getController(String keyUrl) {
        return map.keySet().stream().filter(keyUrl::equals).findAny()
                .map(e -> map.get(e))
                .orElse(new DefaultController());
    }

}
