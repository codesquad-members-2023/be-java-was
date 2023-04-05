package controller;

import java.util.Map;

import model.User;
import util.HttpRequest;

public class UserController {
    /**
     * HTTP Request를 받아 요청 URL을 분석해 비즈니스 로직을 수행하고 뷰를 String으로 반환합니다.
     * @param httpRequest
     * @return view
     */

    public String requestMapping(HttpRequest httpRequest) {
        if (httpRequest.getUrl().equals("/")) {
            httpRequest.setUrl("/index.html");
            return httpRequest.getUrl();
        }

        if (httpRequest.getUrl().equals("/user/create")) {
            return userJoin(httpRequest);
        }

        return httpRequest.getUrl();
    }

    /**
     * httpRequest의 쿼리 파라미터 map을 넘겨받아 User 객체를 생성합니다.
     * DB와의 연결은 아직 구현되지 않았습니다.
     * 회원 가입 후 home으로 redirect하는 view를 리턴합니다.
     * @param httpRequest
     * @return view
     */

    private String userJoin(HttpRequest httpRequest) {
        Map<String, String> params = httpRequest.getParams();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));

        return "redirect:/";
    }
}
