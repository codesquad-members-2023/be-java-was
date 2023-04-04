package model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import webserver.GetMappingProvider;

public class UrlMethodMappingGenerator {
    public static Map<String, Method> generateUrlMethodMapping() {
        Map<String, Method> urlMethodMapping = new HashMap<>();
        GetMappingProvider getMappingProvider = new GetMappingProvider();
        Class<?> loadClass = getMappingProvider.getClass();
        Method[] methods = loadClass.getMethods();

        for (Method m : methods) {
            if (m.getName().startsWith("mapping")) {
                urlMethodMapping.put(getMappingProvider.methodNameToURL(m.getName()), m);
            }
        }

        return urlMethodMapping;
    }
}
