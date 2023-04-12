package controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import annotation.MethodType;
import request.HttpRequest;
import response.HttpResponse;

public interface Controller {
    Map<String, Method> map = new HashMap<>();

    default void initMethodMapping() {
        //어노테이션 메소드 목록을 Reflection으로 불러와서 map에 추가
        for (Method method : this.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getDeclaredAnnotation(MethodType.class);
            if (annotation instanceof MethodType) {
                MethodType methodType = (MethodType)annotation;
                map.put(methodType.value(), method);
            }
        }
    }
    default String process(HttpRequest httpRequest, HttpResponse httpResponse) throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException, InstantiationException {



        Method method = map.get(httpRequest.getMethod());

        String viewName = (String)method.invoke(this.getClass().newInstance(), httpRequest, httpResponse);

        return viewName;
    }
}
