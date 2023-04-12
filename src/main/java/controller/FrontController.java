package controller;

import annotation.RequestMapping;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import request.HttpRequest;
import response.HttpResponse;

public class FrontController {

    private Map<String, Controller> mapper;

    public FrontController() {
        mapper = HandlerMapper.doMapController();
        HandlerMapper.doMapMethods(mapper);
    }

    /**
     * 처리할 Controller를 조회해서 리턴합니다.
     * @param httpRequest
     * @return
     */
    public Controller getHandler(HttpRequest httpRequest) {
        return mapper.get(httpRequest.getUrl());
    }

    public boolean hasMapping(HttpRequest httpRequest) {
        return mapper.containsKey(httpRequest.getUrl());
    }

    /**
     * 들어온 요청을 적합한 Controller에게 위임한다.
     * @param httpRequest
     * @return
     */
    public String dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!hasMapping(httpRequest)) {
            return httpRequest.getUrl();
        }

        Controller controller = getHandler(httpRequest);
        String viewName;
        try {
            viewName = controller.process(httpRequest, httpResponse);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("지원하지 않는 요청입니다.");
        }

        return viewName;
    }

}
