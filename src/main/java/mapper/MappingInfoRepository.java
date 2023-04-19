package mapper;

import annotation.MethodType;
import controller.Controller;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class MappingInfoRepository {

    private static Map<String, Class<?>> exceptionMap = new HashMap<>();
    private static Map<String, Controller> controllerMap = new HashMap<>();
    private final static Map<String, MethodMap> handlerMethodMap = new HashMap<>();

    public static void initMapping() {
        controllerMap = HandlerMapper.doMapController();
        HandlerMapper.doMapMethods(controllerMap);
    }

    public static void initMethodMapping(Controller controller) {
        MethodMap methodMap = new MethodMap();
        //어노테이션 메소드 목록을 Reflection으로 불러와서 map에 추가
        //Proxy 타겟의 메소드를 불러와야해서 getSuperclass 추가
        for (Method method : controller.getClass().getSuperclass().getDeclaredMethods()) {
            Annotation annotation = method.getDeclaredAnnotation(MethodType.class);
            if (annotation instanceof MethodType) {
                MethodType methodType = (MethodType)annotation;
                methodMap.put(methodType.value(), method);
            }
        }
        exceptionMap = ExceptionMapper.doMapException();
        //Controller 이름 - Controller의 Method 리스트 매핑
        handlerMethodMap.put(controller.getClass().getName(), methodMap);
    }

    public static Method getMappedMethod(String handlerName, String method) {
        return handlerMethodMap.get(handlerName).get(method);
    }

    public static Class<?> getException(String exceptionName) {
        return exceptionMap.get(exceptionName);
    }

    public static Controller getHandler(HttpRequest httpRequest) {
        return controllerMap.get(httpRequest.getUrl());
    }

    public static boolean hasMapping(HttpRequest httpRequest) {
        return controllerMap.containsKey(httpRequest.getUrl());
    }

}
