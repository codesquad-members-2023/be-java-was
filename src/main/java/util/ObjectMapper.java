package util;

import annotation.Param;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class ObjectMapper {
    private Class<?> object;
    private Map<String, String> params;

    /**
     * 생성자
     * @param object 만들 클래스
     * @param params 쿼리 파라미터
     */
    public ObjectMapper(Class<?> object, Map<String, String> params) {
        this.object = object;
        this.params = params;
    }

    public Object mapObject() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = getConstructor();  // 생성자 가져오기

        Parameter[] requireParams = constructor.getParameters();    // 생성자 파라미터 배열
        Object[] constructorParam = new Object[requireParams.length];   // 생성자에 넣을 배열

        String paramName;
        for (int i = 0; i < requireParams.length; i++) {
            paramName = requireParams[i].getAnnotation(Param.class).name();
            constructorParam[i] = params.get(paramName);
        }

        return constructor.newInstance(constructorParam);
    }

    /**
     * Object에서 생성자 가져혼다.
     * 파라미터 숫자와 같은 생성자를 필터링하고 모든 파라미터가 다 있는지 확인 후 반환한다.
     * @return 생성자
     */
    private Constructor<?> getConstructor() {
        return Arrays.stream(object.getConstructors())
                .filter(e -> e.getParameterCount() == params.size())
                .filter(this::validConstructor)
                .findAny()
                .orElseThrow(() -> new RuntimeException("올바른 생성자가 없습니다."));
    }

    /**
     * 올바른 생성자인지 확인한다.
     * Param 어노테이션 name이 쿼리 파라미터 key값에 모두 포함되어있는지 확인한다.
     * @param constructor
     * @return
     */
    private boolean validConstructor(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameters())
                .allMatch(p -> params.containsKey(p.getAnnotation(Param.class).name()));
    }
}
