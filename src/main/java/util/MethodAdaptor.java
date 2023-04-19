package util;


import controller.Controller;
import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodAdaptor {
    public String handle(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView, MyHandlerMethod handlerMethod) throws InvocationTargetException, IllegalAccessException {
        Controller handler = handlerMethod.getHandler();
        Method method = handlerMethod.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();

        List<Object> arguments = new ArrayList<>();

        // 더 멋진 방법이 있겠지만 이정도로 하자..
        for(Class<?> parameterType :parameterTypes) {
            if (parameterType == HttpRequest.class) {
                arguments.add(httpRequest);
                continue;
            }
            if (parameterType == HttpResponse.class) {
                arguments.add(httpResponse);
                continue;
            }
            if (parameterType == ModelAndView.class) {
                arguments.add(modelAndView);
            }
        }


        String viewName = (String) method.invoke(handler, arguments.toArray());
        httpResponse.sendRedirect(viewName);
        return viewName;
    }
}
