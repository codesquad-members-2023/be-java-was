package mapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodMap {
    private Map<String, Method> handlerMethodMap;

    public MethodMap() {
        handlerMethodMap = new HashMap<>();
    }

    public void put(String methodName, Method method) {
        handlerMethodMap.put(methodName, method);
    }

    public Method get(String methodName) {
        return handlerMethodMap.get(methodName);
    }
}
