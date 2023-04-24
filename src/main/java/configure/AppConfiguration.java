package configure;

import annotation.Bean;
import controller.Controller;
import controller.DefaultController;
import controller.UserController;
import interceptor.DefaultInterceptor;
import interceptor.Interceptor;
import interceptor.LoginCheckInterceptor;
import servlet.DispatcherServlet;
import session.SessionManager;
import util.HttpRequestParser;
import util.MethodAdaptor;
import view.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// TODO : 현재는 싱글톤 처럼 동작하지는 않는다.
public class AppConfiguration {

    @Bean(name = "httpRequestParser")
    public HttpRequestParser httpRequestParser() {
        return new HttpRequestParser();
    }

    @Bean(name = "dispatcherServlet")
    public DispatcherServlet dispatcherServlet() {
        SessionManager sessionManager = sessionManager();

        Collection<Controller> controllers = new ArrayList<>();
        controllers.add(new UserController(sessionManager));
        controllers.add(new DefaultController()); // 순서 중요 : 고쳐야함;

        Collection<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new DefaultInterceptor(sessionManager));
        interceptors.add(new LoginCheckInterceptor(sessionManager));


        return new DispatcherServlet(
                controllers,
                viewResolver(),
                interceptors,
                new MethodAdaptor(sessionManager)
                );
    }

    //========
    // TODO : 아래도 빈으로 관리할 수 있는 방법을 생각해보자.
    public SessionManager sessionManager() {
        return new SessionManager(new HashMap<>());
    }

    public ViewResolver viewResolver() {
        Collection<View> views = new ArrayList<>();

        // TODO : 폴더에서 파일을 읽어서 자동으로 추가하는 방법이 좋을 뜻
        views.add(new MyView("/css/bootstrap.min.css"));
        views.add(new MyView("/css/styles.css"));

        views.add(new MyView("/fonts/glyphicons-halflings-regular.eot"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.svg"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.ttf"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.woff"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.woff2"));

        views.add(new MyView("/images/80-text.png"));

        views.add(new MyView("/js/bootstrap.min.js"));
        views.add(new MyView("/js/jquery-2.2.0.min.js"));
        views.add(new MyView("/js/scripts.js"));

        views.add(new MyView("/redirect:" ));
        views.add(new MyView("/index.html" ));

        views.add(new MyView("/user/login.html" ));
        views.add(new MyView("/user/form.html" ));
        views.add(new MyView("/user/list.html" ));
        views.add(new MyView("/user/login_failed.html" ));
        views.add(new MyView("/user/profile.html" ));

        return new ViewResolver(views);
    }
}
