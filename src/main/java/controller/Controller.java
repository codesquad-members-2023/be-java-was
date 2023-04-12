package controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {
    default String process(HttpRequest httpRequest, HttpResponse httpResponse) throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException, InstantiationException {
        Map<String, Method> map = new HashMap<>();
        map.put("GET", this.getClass().getMethod("doGet", HttpRequest.class, HttpResponse.class));
        map.put("POST", this.getClass().getMethod("doPost", HttpRequest.class, HttpResponse.class));

        Method method = map.get(httpRequest.getMethod());

        String viewName = (String)method.invoke(this.getClass().newInstance(), httpRequest, httpResponse);

        return viewName;
    }

    default String doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalArgumentException("해당 URL에서 지원하지 않는 Method입니다.");
    }
    default String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalArgumentException("해당 URL에서 지원하지 않는 Method입니다.");
    }
}
