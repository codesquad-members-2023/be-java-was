package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

public class FrontController {
    private final String USER_URL = "/user/";

    // PATH를 받아 작업을 담당하는 컨트롤러 생성자를 호출하여 작업을 시키는 역할을 한다.
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        Controller controller;

        if (path.startsWith(USER_URL)) {     // user 관련 요청을 받는 경우
            controller = AppConfig.getUserController();  // 의존성을 낮추기 위해 추상 타입에 의존하는 것이 좋다.
            controller.run(httpRequest, httpResponse);
            return;
        }

        controller = AppConfig.getViewController();  // 기본 컨트롤러
        controller.run(httpRequest, httpResponse);
    }
}
