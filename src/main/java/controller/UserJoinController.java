package controller;

import java.util.Map;

import model.User;
import request.HttpRequest;
import request.HttpRequestUtils;

public class UserJoinController implements Controller {
    /**
     * httpRequest의 쿼리 파라미터 map을 넘겨받아 User 객체를 생성합니다.
     * DB와의 연결은 아직 구현되지 않았습니다.
     * 회원 가입 후 home으로 redirect하는 view를 리턴합니다.
     * @param httpRequest
     * @return view
     */

    @Override
    public String doPost(HttpRequest httpRequest) {
        Map<String, String> params = HttpRequestUtils.parseQueryParams(httpRequest.getBody());

        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));

        return "redirect:/";
    }
}
