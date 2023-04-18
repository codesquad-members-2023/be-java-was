package controller;

import controller.user.UserJoinController;
import controller.user.UserListController;
import controller.user.UserLoginController;
import service.UserService;

import java.util.Map;

public class HandlerMapper {
    private static final String USER_JOIN = "/user/create";
    private static final String USER_LIST = "/user/list";
    private static final String USER_LOGIN = "/user/login";

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
