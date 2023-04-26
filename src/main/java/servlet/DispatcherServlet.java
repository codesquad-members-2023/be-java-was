package servlet;

import controller.DefaultController;
import interceptor.Interceptor;
import controller.Controller;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MethodAdaptor;
import util.MyHandler;
import view.View;
import view.ModelAndView;
import view.ViewResolver;

import java.io.IOException;
import java.util.Collection;

public class DispatcherServlet implements HttpServlet {

    private final Collection<Controller> controllers;
    private final Collection<Interceptor> interceptors;
    private final ViewResolver viewResolver;
    private final MethodAdaptor methodAdaptor;

    public DispatcherServlet(Collection<Controller> controllers, ViewResolver viewResolver, Collection<Interceptor> interceptors, MethodAdaptor methodAdaptor) {
        this.controllers = controllers;
        this.viewResolver = viewResolver;
        this.interceptors = interceptors;
        this.methodAdaptor = methodAdaptor;
    }

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        // 요청에 맞는 컨트롤러와 메소드를 찾는다.
        MyHandler handler = new MyHandler(httpRequest, controllers);

        logger.info("핸들러 {} : 메소드 {}", handler.getController(), handler.getMethod());

        // DefaultController가 응답할 경우 바로 특별한 로직없이 바로 응답
        if (handler.getController() instanceof DefaultController) {
            // TODO : DispatcherServlet 에 어떠한 로직이 존재하는 것이 마음에 들지 않는다.
            String pathInfo = httpRequest.getPathInfo();
            if(pathInfo.equals("/")) {
                httpResponse.sendRedirect("redirect:/qna/list");
            }
            doResponse(httpResponse, new ModelAndView(), httpRequest.getPathInfo());
        }

        ModelAndView modelAndView = new ModelAndView();

        for (Interceptor interceptor : interceptors) {
            if (!interceptor.preHandle(httpRequest, httpResponse, handler)) {
                String redirectURL = httpResponse.getRedirectURL();
                doResponse(httpResponse, modelAndView, redirectURL);
            }
        }

        String viewName = methodAdaptor.handle(httpRequest, httpResponse, modelAndView, handler);

        doResponse(httpResponse, modelAndView, viewName);
    }

    private void doResponse(HttpResponse httpResponse, ModelAndView modelAndView, String viewName) throws IOException {
        View view = viewResolver.resolve(viewName);
        view.render(modelAndView.getModel(), httpResponse);
    }

}
