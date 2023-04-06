package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructorMapper {

    private static final Logger log = LoggerFactory.getLogger(ConstructorMapper.class);

    // TODO : Optional.empty 개선방법 생각하기, getObject() throw 처리 방법 생각해보기
    public static Optional<?> readValue(String requestParam, Class<?> inputClass) {

        List<String> params = parseRequestParams(requestParam);

        Constructor<?> constructor = findConstructor(inputClass, params);
        if (constructor == null) {
            return Optional.empty();
        }
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> constructorVariables = convertType(params, parameterTypes);

        return getObject(constructorVariables, constructor);
    }


    private static List<String> parseRequestParams(String requestParam) {
        List<String> params = new ArrayList<>();
        String[] information = requestParam.split("&");

        for (String param : information) {
            String decodeParam = URLDecoder.decode(param.split("=")[1], StandardCharsets.UTF_8);
            params.add(decodeParam);
        }

        return params;
    }

    private static Constructor<?> findConstructor(Class<?> inputClass, List<String> params) {
        Constructor<?> constructor = null;
        for (Constructor<?> cons : inputClass.getConstructors()) {
            // 입력된 클래스에서 모든 생성자 조회해서 파라미터 길이 같은 생성자 조회
            if (cons.getParameterTypes().length != params.size()) {
                continue;
            }
            constructor = cons;
        }
        return constructor;
    }

    private static List<Object> convertType(List<String> params, Class<?>[] parameterTypes) {
        List<Object> constructorVariables = new ArrayList<>();

        for (int i = 0; i < params.size(); i++) {
            String parameter = params.get(i);
            Class<?> parameterType = parameterTypes[i];
            constructorVariables.add(parameterType.cast(parameter));
        }

        return constructorVariables;
    }

    private static Optional<?> getObject(List<Object> constructorVariables, Constructor<?> constructor) {
        try {
            return Optional.of(constructor.newInstance(constructorVariables.toArray()));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
