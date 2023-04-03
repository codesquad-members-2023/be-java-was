package controller;

public class UserController {
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

}
