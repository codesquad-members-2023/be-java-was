package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import util.RequestParser;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());
    // 인스턴스 변수
    private static UserController instance;

    // private 생성자
    private UserController() {

    }

    // 인스턴스 반환 메소드
    public static UserController getInstance() {
        if (instance == null) {
            synchronized (UserController.class) {
                if (instance == null) {
                    instance = new UserController();
                }
            }
        }
        return instance;
    }

    public String mapToFunctions(HttpRequest httpRequest) {

        String data = null;
        String httpMethod = httpRequest.getValueByNameInRequestLine("httpMethod");
        String resourceUrl = httpRequest.getValueByNameInRequestLine("resourceUrl");

        if (resourceUrl.contains("?")) {
            String[] parsedData = RequestParser.parseQueryParameter(resourceUrl);
            resourceUrl = parsedData[0];
            data = parsedData[1];
            log.debug("resourceUrl = [{}], data = [{}]", resourceUrl, data);
        }

        if (httpMethod.equals("GET") && resourceUrl.equals("/user/create") && !Objects.isNull(data)) {
            return getSignUpUserFromQueryParameter(data);
        }

        if (httpMethod.equals("GET") && resourceUrl.equals("/user/form.html")) {
            return getUserSignUpFormPage(resourceUrl);
        }

        if (httpMethod.equals("POST") && resourceUrl.equals("/user/create")) {
            return postNewUser(httpRequest);
        }

        return "/error.html";
    }

    private String getSignUpUserFromQueryParameter(String data) {

        Map<String, String> userInfo = Arrays.stream(data.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(key -> key[0], value -> value[1]));

        Database.addUser(new User(userInfo));
        return "/index.html";
    }

    private String getUserSignUpFormPage(String parsedUrl) {
        return "/" + parsedUrl;
    }

    private String postNewUser(HttpRequest httpRequest) {
        Map<String, String> userInfo = httpRequest.getHttpRequestBody();

        Database.addUser(new User(userInfo));

        return "redirect:/index.html";
    }
}
