package controller;

import config.AppConfig;
import util.HttpRequest;
import util.HttpResponse;

public class URLController {

    UserController userController = AppConfig.userController();


    public String mapUrl(String path, HttpRequest httpRequest, HttpResponse httpResponse) {
        // localhost:8080 기본화면으로 이동
        if (path.equals("/")) {
            return "/index.html";
        }
        // userController 호출
        if (path.startsWith("/user")) {
            return userController.process(httpRequest, httpResponse);
        }

        // 기본 경로로 이동
        return path;
    }
}
