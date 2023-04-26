package util;

import controller.Controller;
import model.User;
import servlet.HttpRequest;
import servlet.HttpResponse;
import session.SessionManager;
import view.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodAdaptor {

    private MyArgumentResolver myArgumentResolver;
    private SessionManager sessionManager;

    public MethodAdaptor(MyArgumentResolver myArgumentResolver, SessionManager sessionManager) {
        this.myArgumentResolver = myArgumentResolver;
        this.sessionManager = sessionManager;
    }

    public String handle(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView, MyHandler handlerMethod) throws InvocationTargetException, IllegalAccessException {
        Controller controller = handlerMethod.getController();
        Method method = handlerMethod.getMethod();

        List<Object> arguments = myArgumentResolver.resolve(httpRequest, httpResponse, modelAndView, handlerMethod);

        String viewName = (String) method.invoke(controller, arguments.toArray());
        httpResponse.sendRedirect(viewName);

        User user = (User) sessionManager.getSession(httpRequest);
        if (user != null) {
            modelAndView.addModel("user", user);
            modelAndView.addModel("login", true);
        }
        return viewName;
    }

}
