package config;

import controller.Controller;
import controller.UserController;
import controller.ViewController;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {
    public static final String USER_URL = "/user/";
    private Map<String, Controller> mapping;

    public AppConfig() {
        mapping = new HashMap<>();
        mapping.put(USER_URL, new UserController());
    }

    public Controller getController(String keyUrl) {
        return mapping.keySet().stream().filter(keyUrl::startsWith).findAny()
                .map(e -> mapping.get(e))
                .orElse(new ViewController());
    }

}
