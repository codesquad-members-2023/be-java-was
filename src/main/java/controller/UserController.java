package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

import java.util.Objects;

public class UserController {

        private Logger log = LoggerFactory.getLogger(getClass());
        // 인스턴스 변수
        private static UserController instance;

        // private 생성자
        public UserController() {

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

        public String mapToFunctions(String requestLine) {

            String[] parsedUrl = RequestParser.separateUrls(requestLine);

            String httpMethod = parsedUrl[0];
            String resourceUrl = parsedUrl[1];
            String httpVersion = parsedUrl[2];
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

            return "/index.html";
        }

    private String getSignUpUserFromQueryParameter(String httpMethod, String parsedUrl, String data) {

        if (!httpMethod.equals("GET")) {
            return "/util/error";
        }

        int numOfDataForSignUp = 4;
        String[] userInfo = data.split("\\&");
        String[] values = new String[numOfDataForSignUp];

        for (int i = 0; i < numOfDataForSignUp; i++) {
            String[] splited = userInfo[i].split("\\=");
            String value = splited[1];
            values[i] = value;
        }

        Database.addUser(new User(values[0], values[1], values[2], values[3]));
        return "/index.html";
    }
}
