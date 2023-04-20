package mapper;

import controller.Controller;
import interceptor.Interceptor;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import annotation.RequestMapping;
import net.sf.cglib.proxy.Enhancer;

public class HandlerMapper {
    /**
     * Reflection을 이용해 controller 패키지에서 Controller 인터페이스를 제외한 구현체 class를 읽어옵니다.
     * class의 Annotation URL 값을 가져와 매핑을 등록해줍니다.
     */
    public static Map<String, Controller> doMapController() {
        Map<String, Controller> mapper = new HashMap<>();
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> controllerInterface = Controller.class;

            String packageName = "controller"; // 패키지명
            String packagePath = packageName.replace('.', '/'); // 패키지명을 디렉토리 경로로 변환

            //패키지 URL을 불러온다.
            URL packageUrl = classLoader.getResource(packagePath);
            //패키지에서 URLClassLoader 객체를 생성한다.
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {packageUrl}, classLoader);

            //package 디렉토리를 읽어온다.
            File packageDir = new File(packageUrl.getFile());

            //package 경로에서 class 파일을 읽어온다.
            File[] classFiles = packageDir.listFiles(file -> file.getName().endsWith(".class"));

            //모든 클래스의 Mapping 정보를 업데이트한다.
            for (File classFile : classFiles) {
                String className = packageName + "." + classFile.getName().replace(".class", "");
                Class<?> controllerImpl = urlClassLoader.loadClass(className);

                //Controller 인터페이스를 구현하면서 Controller 인터페이스가 아닌 클래스 구현체만 mapping시킨다.
                if (controllerInterface.isAssignableFrom(controllerImpl) && !controllerImpl.equals(
                        Controller.class)) {
                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(controllerImpl);

                    enhancer.setCallback(new Interceptor());
                    Controller proxyController = (Controller) enhancer.create();

                    mapper.put(controllerImpl.getDeclaredAnnotation(RequestMapping.class).url(),
                            proxyController);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mapper;
    }

    public static void doMapMethods(Map<String, Controller> mapper) {
        for (Controller controller : mapper.values()) {
            MappingInfoRepository.initMethodMapping(controller);
        }
    }
}
