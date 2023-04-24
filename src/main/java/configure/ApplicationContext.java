package configure;

import annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private final Map<String, Object> beanMap;

    public ApplicationContext(AppConfiguration appConfigure) throws InvocationTargetException, IllegalAccessException {
        beanMap = new HashMap<>();

        Method[] methods = appConfigure.getClass().getMethods();

        for (Method method : methods) {
            addBean(appConfigure, method);
        }

    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    private void addBean(AppConfiguration appConfigure, Method method) throws IllegalAccessException, InvocationTargetException {
        Bean bean = method.getAnnotation(Bean.class);
     
        if (bean != null) {
            beanMap.put(bean.name(), method.invoke(appConfigure));
        }
    }
}
