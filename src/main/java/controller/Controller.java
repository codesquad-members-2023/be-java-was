package controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import annotation.ExceptionHandler;
import annotation.MethodType;
import mapper.ExceptionMapper;
import request.HttpRequest;
import response.HttpResponse;

public abstract class Controller {

    Map<String, Method> handlerMethodMap;
    Map<String, Class<?>> exceptionMap;

    public void initMethodMapping() {
        handlerMethodMap = new HashMap<>();
        //어노테이션 메소드 목록을 Reflection으로 불러와서 map에 추가
        for (Method method : this.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getDeclaredAnnotation(MethodType.class);
            if (annotation instanceof MethodType) {
                MethodType methodType = (MethodType) annotation;
                handlerMethodMap.put(methodType.value(), method);
            }
        }
        exceptionMap = ExceptionMapper.doMapException();
    }

    public String process(HttpRequest httpRequest, HttpResponse httpResponse) throws
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException, InstantiationException {
        Method method = handlerMethodMap.get(httpRequest.getMethod());

        String viewName = "";
        try {
            viewName = (String) method.invoke(this.getClass().newInstance(), httpRequest,
                httpResponse);
        } catch (InvocationTargetException e) {
            // 컨트롤러가 호출한 메서드에서 예외 발생 시 handler 로직 실행
            viewName = handleException(viewName, e);
        }
        return viewName;
    }

    private String handleException(String viewName, InvocationTargetException e)
        throws IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean errorHandledFlag = false;
        // 모든 메소드를 불러온다.
        Method[] exceptionHandlers = this.getClass().getDeclaredMethods();
        for (Method exceptionMethod : exceptionHandlers) {
            //메소드 중 ExceptionHandler 어노테이션이 있으면 어노테이션을 불러온다.
            if (exceptionMethod.isAnnotationPresent(ExceptionHandler.class)) {
                Annotation annotation = exceptionMethod.getDeclaredAnnotation(
                    ExceptionHandler.class);
                // ExceptionMapper 클래스에서 등록해둔 클래스이름 - 클래스 map에서 일치하는 클래스를 가져온다.
                Class<?> exception = exceptionMap.get(((ExceptionHandler) annotation).exception());
                // 발생한 Invocation으로 Wrapping된 예외가 annotation가 일치하면 예외 로직을 실행한다.
                if (exception.isInstance(e.getTargetException())) {
                    viewName = (String)exceptionMethod.invoke(this.getClass().newInstance());
                    errorHandledFlag = true;
                }
            }
        }
        //Handler가 예외를 처리하지 않았으면 다시 던져준다.
        if (!errorHandledFlag) {
            throw new RuntimeException("Unhandled Exception Occured");
        }
        return viewName;
    }
}
