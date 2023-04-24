package util;

import controller.Controller;
import type.RequestMethod;

import java.lang.reflect.Method;
import java.util.Collection;

public class RequestMapping {

    // TODO : 코드 정리가 필요한 시점!
    public static Method requestMethodMapping(Class<? extends Controller> controller, String url, RequestMethod requestMethod) {
        Method[] methods = controller.getMethods();
        annotation.RequestMapping controllerAnnotation = controller.getAnnotation(annotation.RequestMapping.class);
        String[] controllerPaths = controllerAnnotation.path();

        for (Method method : methods) {
            annotation.RequestMapping methodAnnotation = method.getAnnotation(annotation.RequestMapping.class);
            
            if (methodAnnotation == null || methodAnnotation.method() != requestMethod) {
                continue;
            }

            String[] methodPaths = methodAnnotation.path();

            // TODO : 2 중 포문이긴 한데, 더 좋은 방법이 있을까?
            for (String controllerPath : controllerPaths) {
                for (String methodPath : methodPaths) {
                    String path = controllerPath + methodPath;
                    if (url.startsWith(path)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
    public static Controller requestControllerMapping(Collection<Controller> controllers, String pathInfo) {
        for (Controller controller : controllers) {
            annotation.RequestMapping conRequestMapping = controller.getClass().getAnnotation(annotation.RequestMapping.class);
            String[] controllerPaths = conRequestMapping.path();
            for (String controllerPath: controllerPaths) {
                if (pathInfo.startsWith(controllerPath)) {
                    return controller;
                }
            }
        }
        return null;
    }
}
