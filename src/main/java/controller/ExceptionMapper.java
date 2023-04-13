package controller;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import annotation.ExceptionHandler;
import annotation.RequestMapping;

public class ExceptionMapper {
    public static Map<String, Class<?>> doMapException() {
        Map<String, Class<?>> mapper = new HashMap<>();

        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            String packageName = "exception"; // 패키지명
            String packagePath = packageName.replace('.', '/'); // 패키지명을 디렉토리 경로로 변환

            //패키지 URL을 불러온다.
            URL packageUrl = classLoader.getResource(packagePath);
            //패키지에서 URLClassLoader 객체를 생성한다.
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {packageUrl}, classLoader);

            //package 디렉토리를 읽어온다.
            File packageDir = new File(packageUrl.getFile());

            System.out.println(packageDir);
            //package 경로에서 class 파일을 읽어온다.
            File[] classFiles = packageDir.listFiles(file -> file.getName().endsWith(".class"));

            //모든 클래스의 Mapping 정보를 업데이트한다.
            for (File classFile : classFiles) {
                System.out.println(classFile);
                String className = packageName + "." + classFile.getName().replace(".class", "");
                System.out.println(className);
                Class<?> exception = urlClassLoader.loadClass(className);
                System.out.println(exception);

                // 예시 : (UserInfoException.class(String), userinfoexception 클래스)로 map에 저장
                mapper.put(classFile.getName(), exception);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(mapper);
        return mapper;
    }

    public static Class<?> getException() {
        return null;
    }
}
