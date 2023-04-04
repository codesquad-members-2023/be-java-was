package controller;

import util.RequestParser;

public class UserController {
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

        public String mapToFunctions(String[] parsedUrl) {

            String httpMethod = parsedUrl[0];
            String resourceUrl = parsedUrl[1];
            String httpVersion = parsedUrl[2];
            String data;

            if (resourceUrl.contains("?")) {
                resourceUrl = RequestParser.parseQueryParameter(resourceUrl)[0];
                data = RequestParser.parseQueryParameter(resourceUrl)[1];
            }

            return "test";
        }
}
