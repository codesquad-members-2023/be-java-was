package util;

import annotation.PathVariable;
import annotation.RequestMapping;
import controller.Controller;
import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MyArgumentResolver {


    public List<Object> resolve(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView, MyHandler handler) {
        Controller controller = handler.getController();
        Method method = handler.getMethod();

        Parameter[] parameters = method.getParameters();

        List<Object> arguments = new ArrayList<>();

        for (Parameter parameter : parameters) {
            PathVariable parameterAnnotation = parameter.getAnnotation(PathVariable.class);
            Class<?> parameterType = parameter.getType();

            if (parameterAnnotation == null) {
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
                    continue;
                }
            }

            String[] handlerPathInfos = getHandlerPaths(handler);
            String[] requestPathInfos = httpRequest.getPathInfo().split("/");

            String parameterPathName ="{" + parameterAnnotation.name() + "}";

            for (int i = 0; i < handlerPathInfos.length; i++) {
                if (handlerPathInfos[i].equals(parameterPathName)) {
                    arguments.add(convertType(parameterType, requestPathInfos[i]));
                    break;
                }
            }
        }
        return arguments;
    }

    private String[] getHandlerPaths(MyHandler handlerMethod) {
        Controller controller = handlerMethod.getController();
        Method method = handlerMethod.getMethod();

        String controllerAnnotationPathInfo = controller.getClass().getAnnotation(RequestMapping.class).path()[0];
        String methodAnnotationPathInfos = method.getAnnotation(RequestMapping.class).path()[0];

        String pathInfos = controllerAnnotationPathInfo + methodAnnotationPathInfos;

        return pathInfos.split("/");
    }

    private static Object convertType(Class<?> clazz, String value) {
        Object object = null;
        if (clazz == String.class) {
            return value;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return Integer.parseInt(value);
        }
        return null;
    }
}
