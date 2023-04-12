package controller;

import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    public static final String USER_URL = "/user";
    private Map<String, Controller> mapping;

    public HandlerMapping() {
        mapping = new HashMap<>();
        mapping.put(USER_URL, new UserController(new UserService()));
    }

    public Controller getController(String keyUrl) {
        return mapping.keySet().stream().filter(keyUrl::startsWith).findAny()
                .map(e -> mapping.get(e))
                .orElse(new DefaultController());
    }

}
