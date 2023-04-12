package controller;

import db.Database;
import model.User;
import util.ProtocolParser;
import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.StatusCode;

import java.io.IOException;
import java.util.Map;

import static controller.HandlerMapping.USER_URL;

public class UserController extends FrontController {
    /**
     * 작업을 처리할 메서드를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward(StatusCode.OK, httpRequest.getPath()).response();
        return httpRequest.getPath();
    }

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (httpRequest.isPath(USER_URL + "/create")) {
                return join(httpRequest, httpResponse);
            }
            if (httpRequest.isPath(USER_URL + "/login")) {
                return login(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return httpRequest.getPath();
    }

    private String login(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
        String userId = parameter.get("userId");
        String password = parameter.get("password");

        User user;
        if ((user=Database.findUserById(userId))==null || !user.isLogined(password)) {
            httpResponse.forward(StatusCode.UNAUTHORIZED, "/user/login_failed.html").response();
            return "/user/login_failed.html";
        }

        logger.info("[LOGIN SUCCESS!!] userId = {}, password = {}", userId, password);
        httpResponse.setCookie("SID", "logined")   // SID cookie 세팅
                .setCookie("Max-Age", "14400")  // 만료시간 4시간
                .setCookie("Path", "/")         // 유효 범위
                .redirect("/")            // 완료 후 index로 리다이렉트
                .response();
        return "redirect:/";
    }

    private String join(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> parameter = ProtocolParser.parseParameter(httpRequest.getBody());
        String userId = parameter.get("userId");
        String password = parameter.get("password");
        String name = parameter.get("name");
        String email = parameter.get("email");
        User user = new User(userId, password, name, email);

        Database.addUser(user);

        logger.info("[WELCOME] NEW USER = {}", user);

        httpResponse.redirect("/").response();

        return "redirect:/";
    }


}
