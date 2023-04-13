package controller;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import annotation.ExceptionHandler;
import annotation.MethodType;
import annotation.RequestMapping;
import request.HttpRequest;
import response.HttpResponse;

public abstract class Controller {
    Map<String, Method> handlerMethodMap;

    public void initMethodMapping() {
        handlerMethodMap = new HashMap<>();
        //어노테이션 메소드 목록을 Reflection으로 불러와서 map에 추가
        for (Method method : this.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getDeclaredAnnotation(MethodType.class);
            if (annotation instanceof MethodType) {
                MethodType methodType = (MethodType)annotation;
                handlerMethodMap.put(methodType.value(), method);
            }
        }
    }

    public String process(HttpRequest httpRequest, HttpResponse httpResponse) throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException, InstantiationException {
        Method method = handlerMethodMap.get(httpRequest.getMethod());

        Map<String, Class<?>> mapper = ExceptionMapper.doMapException();

        String viewName = "";

        try {
            viewName = (String)method.invoke(this.getClass().newInstance(), httpRequest, httpResponse);
        } catch (InvocationTargetException e) {
            // 예외 catch 시 handler 로직 실행

            Method exceptionMethod = this.getClass().getDeclaredMethod("handle");
            if (exceptionMethod != null) {
                Annotation annotation = exceptionMethod.getDeclaredAnnotation(ExceptionHandler.class);
                if (annotation instanceof ExceptionHandler) {
                    // 처리하려는 예외와 비교해서 일치할 때만 Handling
                    Class<?> exception = mapper.get(((ExceptionHandler)annotation).exception());
                    System.out.println(exception);
                    // Invocation으로 던져지는데 어떤 타입의 예외인지 어떻게 알지?..
                    exceptionMethod.invoke(this.getClass().newInstance());
                }
                //Handler가 없을 때는 다시 던져준다.
                else {
                    throw new RuntimeException("Unhandled Exception Occured");
                }
            }
        }

        return viewName;
    }
}
