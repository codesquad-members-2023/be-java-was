package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import request.HttpRequest;
import response.HttpResponse;

public class FrontController {

    private Map<String, Controller> handlerControllerMap;

    public FrontController() {
        handlerControllerMap = HandlerMapper.doMapController();
        HandlerMapper.doMapMethods(handlerControllerMap);
    }

    /**
     * 처리할 Controller를 조회해서 리턴합니다.
     * @param httpRequest
     * @return
     */
    public Controller getHandler(HttpRequest httpRequest) {
        return handlerControllerMap.get(httpRequest.getUrl());
    }

    public boolean hasMapping(HttpRequest httpRequest) {
        return handlerControllerMap.containsKey(httpRequest.getUrl());
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
