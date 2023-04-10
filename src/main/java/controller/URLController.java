package controller;

import config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequest;
import util.HttpResponse;

public class URLController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private UserController userController = AppConfig.userController();


    public String mapUrl(String path, HttpRequest httpRequest, HttpResponse httpResponse) {
        // localhost:8080 기본화면으로 이동
        if (path.equals("/")) {
            httpResponse.setStatus(200);
            log.info("기본 화면으로 이동 ={}", path);
            return "/index.html";
        }
        // userController 호출
        if (path.startsWith("/user")) {
            log.info("userController 호출 = {}", path);
            return userController.process(httpRequest, httpResponse);
        }

        // 기본 경로로 이동
        log.info("들어온 경로 그대로 이동 = {}", path);
        httpResponse.setStatus(200);
        return path;
    }
}
