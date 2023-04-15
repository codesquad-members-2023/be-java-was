package servlet;

import controller.Controller;
import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import type.RequestMethod;
import util.RequestMapping;

import java.lang.reflect.Method;
import java.util.Collection;

public class DispatcherServlet implements HttpServlet {

    private final Collection<Controller> controllers;

    public DispatcherServlet(Collection<Controller> controllers) {
        this.controllers = controllers;
    }

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        String pathInfo = httpRequest.getPathInfo();
        String requestMethod = httpRequest.getMethod();

        // URL 을 처리할 수 있는 컨트롤러를 찾는다.
        Controller serviceController = RequestMapping.requestControllerMapping(controllers, pathInfo);
        if (serviceController == null) {
            defaultService(httpRequest, httpResponse);
            return;
        }

        // URL 을 처리할 수 있는 핸들러를 찾는다.
        Method method = RequestMapping.requestHandlerMapping(serviceController.getClass(), pathInfo, RequestMethod.getMethod(requestMethod));
        if (method == null) {
            logger.info("메서드를 찾지 못했습니다.");
            return;
        }
        method.invoke(serviceController, httpRequest, httpResponse);
    }

    // TODO : 애초에 디폴트 서비스란게 있나?
    // TODO : 서버에게 의도치 않는 URL 이 오면 예외처리가 맞는 것 같다. (수정 필요)
    private void defaultService(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getPathInfo();
        httpResponse.addHeader("Request-URL", requestUrl);
    }
}
