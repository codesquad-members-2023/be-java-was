package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

import java.util.*;
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

        public String mapToFunctions(String httpMethod, String resourceUrl) {

            String data = null;

            if (resourceUrl.contains("?")) {
                String[] parsedData = RequestParser.parseQueryParameter(resourceUrl);
                resourceUrl = parsedData[0];
                data = parsedData[1];
                log.debug("resourceUrl = [{}], data = [{}]", resourceUrl, data);
            }

            if (httpMethod.equals("GET") && resourceUrl.equals("/user/create") && !Objects.isNull(data)) {
                return getSignUpUserFromQueryParameter(httpMethod, resourceUrl, data);
            }

            if (httpMethod.equals("GET") && resourceUrl.equals("/user/form.html")) {
                return getUserSignUpFormPage(httpMethod, resourceUrl);
            }

            return "/util/error.html";
        }

    private String getSignUpUserFromQueryParameter(String httpMethod, String parsedUrl, String data) {

        Map<String, String> userInfo = Arrays.stream(data.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(key -> key[0], value -> value[1]));

        Database.addUser(new User(userInfo));
        return "/index.html";
    }

    private String getUserSignUpFormPage(String httpMethod, String parsedUrl) {
        return "/" + parsedUrl;
    }
}
