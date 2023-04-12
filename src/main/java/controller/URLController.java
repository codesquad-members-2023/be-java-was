package controller;

import config.AppConfig;
import cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class URLController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserController userController = AppConfig.userController();


    public String mapUrl(String path, HttpRequest httpRequest, HttpResponse httpResponse, BufferedReader br, Cookie cookie) throws IOException {
        // localhost:8080 기본화면으로 이동
        if (path.equals("/")) {
            httpResponse.setStatus(200);
            log.info("기본 화면으로 이동 ={}", path);
            return "/index.html";
        }
        // userController 호출
        if (path.startsWith("/user")) {
            log.info("userController 호출 = {}", path);
            return userController.process(httpRequest, httpResponse, br, cookie);
        }

        // 기본 경로로 이동
        log.info("들어온 경로 그대로 이동 = {}", path);
        httpResponse.setStatus(200);
        return path;
    }
}
