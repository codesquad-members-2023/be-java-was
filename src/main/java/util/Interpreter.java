package util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private Map<String, Object> models;

    public Interpreter(Map<String, ?> models) {
        this.models = new HashMap<>(models);
    }

    public Interpreter() {
        models = new HashMap<>();
    };

    public void addModel(String name, Object value) {
        this.models.put(name, value);
    }


    public String interpretValue(String input) {
        String name = getObjectName(input);
        Object object = findObjectBy(name);
        return object.toString();
    }

    public Object interpretObject(String name) {
        return findObjectBy(name);
    }

    public String interpretObjectAndMethodName(String objectNameAndMethod) {
        String objectName = getObjectName(objectNameAndMethod);
        String methodName = getMethodName(objectNameAndMethod);
        Object object = findObjectBy(objectName);
        return interpretObjectAndMethodName(object, methodName);
    }

    public String interpretObjectAndMethodName(Object object, String methodName) {
        assert object != null;
        Class<?> clazz = object.getClass();

        String output = null;
        try {
            Method method = clazz.getMethod(methodName);
            Object invoke = method.invoke(object);

            output = invoke.toString();
        } catch (Exception e) {
        }

        return output;
    }

    private Object findObjectBy(String name) {
        for (String key : models.keySet()) {
            if (key.equals(name)) {
                return models.get(key);
            }
        }
        return null;
    }

    private String getObjectName(String input) {
        return input.split("\\.")[0];
    }
    private String getMethodName(String input) {
        return input.split("\\.")[1];
    }
}
