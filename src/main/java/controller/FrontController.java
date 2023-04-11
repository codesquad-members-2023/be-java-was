package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import request.HttpRequest;

public class FrontController {

    private Map<String, Controller> mapper = new HashMap<>();

    public FrontController() {
        initMapping();
    }

    private void initMapping() {
        mapper.put("/", new ArticleController());
        mapper.put("/users/create", new UserJoinController());
    }

    public Controller getHandler(HttpRequest httpRequest) {
        return mapper.get(httpRequest.getUrl());
    }

    public boolean hasMapping(HttpRequest httpRequest) {
        return mapper.containsKey(httpRequest.getUrl());
    }

    public String dispatch(HttpRequest httpRequest) {
        if (!hasMapping(httpRequest)) {
            return httpRequest.getUrl();
        }
        Controller controller = getHandler(httpRequest);
        String viewName;
        try {
            viewName = controller.process(httpRequest);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("지원하지 않는 요청입니다.");
        }

        return viewName;
    }

}
