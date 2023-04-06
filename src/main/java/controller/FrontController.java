package controller;

import config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;

public class FrontController {
    private Controller controller;
    private final String USER_URL = "/user/";

    /**
     * 작업을 처리할 컨트롤러를 호출한다.
     * @param httpRequest
     * @param httpResponse
     */
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();

        if (path.startsWith(USER_URL)) {     // user 관련 요청을 받는 경우
            controller = AppConfig.getUserController();  // 의존성을 낮추기 위해 추상 타입에 의존하는 것이 좋다.
            controller.run(httpRequest, httpResponse);
            return;
        }

        controller = AppConfig.getViewController();  // 기본 컨트롤러
        controller.run(httpRequest, httpResponse);
    }
}
