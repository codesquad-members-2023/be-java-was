package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpResponse;
import webserver.protocol.StyleType;

import java.io.IOException;

public class FrontController {
    private Logger logger = LoggerFactory.getLogger(FrontController.class);

    // PATH를 받아 작업을 담당하는 컨트롤러 생성자를 호출하여 작업을 시키는 역할을 한다.
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        Controller controller;

        if (StyleType.anyMatchStyle(path).isPresent()) {   // style 요청을 받은 경우
            logger.info("path = {}", httpRequest.getPath());
            controller = new StyleController();
            controller.run(httpRequest, httpResponse);
        }

        if (path.startsWith("/user")) {     // user 관련 요청을 받은 경우
            controller = new UserController();
            controller.run(httpRequest, httpResponse);
        }

        controller = new ViewController();  // 기본값 컨트롤러
        controller.run(httpRequest, httpResponse);
    }
}
