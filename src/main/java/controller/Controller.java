package controller;

import annotation.ExceptionHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mapper.MappingInfoRepository;
import request.HttpRequest;
import response.HttpResponse;

public abstract class Controller {

    public String process(HttpRequest httpRequest, HttpResponse httpResponse) throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException, InstantiationException {
        Method method = MappingInfoRepository.getMappedMethod(this.getClass().getName(), httpRequest.getMethod());

        String viewName = "";
        try {
            viewName = (String)method.invoke(this.getClass().newInstance(), httpRequest,
                    httpResponse);
        } catch (InvocationTargetException e) {
            // 컨트롤러가 호출한 메서드에서 예외 발생 시 handler 로직 실행
            viewName = handleException(viewName, e, httpRequest, httpResponse);
        }
        return viewName;
    }

    private String handleException(String viewName, InvocationTargetException e, HttpRequest httpRequest,
            HttpResponse httpResponse) throws
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        boolean errorHandledFlag = false;
        // 모든 메소드를 불러온다.
        Method[] exceptionHandlers = this.getClass().getDeclaredMethods();
        for (Method exceptionMethod : exceptionHandlers) {
            //메소드 중 ExceptionHandler 어노테이션이 있으면 어노테이션을 불러온다.
            if (exceptionMethod.isAnnotationPresent(ExceptionHandler.class)) {
                Annotation annotation = exceptionMethod.getDeclaredAnnotation(
                        ExceptionHandler.class);
                // ExceptionMapper 클래스에서 등록해둔 클래스이름 - 클래스 map에서 일치하는 클래스를 가져온다.
                Class<?> exception = MappingInfoRepository.getException(((ExceptionHandler)annotation).exception());
                // 발생한 Invocation으로 Wrapping된 예외가 annotation가 일치하면 예외 로직을 실행한다.
                if (exception.isInstance(e.getTargetException())) {
                    viewName = (String)exceptionMethod.invoke(this.getClass().newInstance(), httpRequest, httpResponse);
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
