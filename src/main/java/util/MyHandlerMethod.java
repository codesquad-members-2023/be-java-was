package util;

import controller.Controller;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.HttpRequest;
import type.RequestMethod;

import java.lang.reflect.Method;
import java.util.Collection;

public class MyHandlerMethod {
    private Controller handler;
    private Method method;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public MyHandlerMethod(HttpRequest httpRequest, Collection<Controller> controllers) throws ClassNotFoundException {
        String pathInfo = httpRequest.getPathInfo();
        String requestMethod = httpRequest.getMethod();

        // URL 을 처리할 수 있는 컨트롤러를 찾는다.
        this.handler = RequestMapping.requestControllerMapping(controllers, pathInfo);
        if (this.handler == null) {
            logger.info("컨트롤러를 찾지 못했습니다.");
            throw new ClassNotFoundException();
        }
        // 컨트롤러에서 URL 을 처리할 수 있는 핸들러를 찾는다.
        this.method = RequestMapping.requestHandlerMapping(handler.getClass(), pathInfo, RequestMethod.getMethod(requestMethod));
        if (method == null) {
            logger.info("메서드를 찾지 못했습니다.");
        }
    }

    public Controller getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }
}
