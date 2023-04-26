package configure;

import annotation.Bean;
import controller.ArticleController;
import controller.Controller;
import controller.DefaultController;
import controller.UserController;
import db.ArticleDatabase;
import db.UserDatabase;
import interceptor.DefaultInterceptor;
import interceptor.Interceptor;
import interceptor.LoginCheckInterceptor;
import service.ArticleService;
import service.UserService;
import servlet.DispatcherServlet;
import session.SessionManager;
import util.HttpRequestParser;
import util.MethodAdaptor;
import util.MyArgumentResolver;

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
        ArticleService articleService = articleService();
        UserService userService = userService();

        Collection<Controller> controllers = new ArrayList<>();
        // TODO : 내가 객체들간의 관계를 생각해서 설정해주는 것은 귀찮다.
        // TODO : 빈 테이블 같은 것을 만들어서, 자동으로 싱글톤으로 객체들을 만들고 관계를 맺게 할 수 있지 않을까?
        controllers.add(new UserController(userService, sessionManager));
        controllers.add(new ArticleController(articleService));
        controllers.add(new DefaultController()); // 순서 중요 : 고쳐야함;


        Collection<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new DefaultInterceptor(sessionManager));
        interceptors.add(new LoginCheckInterceptor(sessionManager));


        return new DispatcherServlet(
                controllers,
                viewResolver(),
                interceptors,
                new MethodAdaptor(myArgumentResolver(), sessionManager)
                );
    }

    //========

    public MyArgumentResolver myArgumentResolver() {
        return new MyArgumentResolver();
    }

    // TODO : 아래도 빈으로 관리할 수 있는 방법을 생각해보자.
    public SessionManager sessionManager() {
        return new SessionManager(new HashMap<>());
    }


    public UserDatabase userDatabase() {
        // TODO : 설정 파일을 하나 만들어 두고, 파일을 읽어서 할 수 있지 않을까?
        return new UserDatabase("jdbc:mysql://127.0.01:3306/codesquad","root", "1234");
    }

    public ArticleDatabase articleDatabase() {
        return new ArticleDatabase("jdbc:mysql://127.0.01:3306/codesquad","root", "1234");
    }

    public UserService userService() {
        return new UserService(userDatabase());
    }

    public ArticleService articleService() {return new ArticleService(articleDatabase()); }

    public ViewResolver viewResolver() {
        Collection<View> views = new ArrayList<>();

        // TODO : 폴더에서 파일을 읽어서 자동으로 추가하는 방법이 좋을 뜻
        views.add(new MyView("/css/bootstrap.min.css", "/css/bootstrap.min.css"));
        views.add(new MyView("/css/styles.css", "/css/styles.css"));

        views.add(new MyView("/fonts/glyphicons-halflings-regular.eot", "/fonts/glyphicons-halflings-regular.eot"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.svg", "/fonts/glyphicons-halflings-regular.svg"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.ttf", "/fonts/glyphicons-halflings-regular.ttf"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.woff", "/fonts/glyphicons-halflings-regular.woff"));
        views.add(new MyView("/fonts/glyphicons-halflings-regular.woff2", "/fonts/glyphicons-halflings-regular.woff2"));

        views.add(new MyView("/images/80-text.png", "/images/80-text.png"));

        views.add(new MyView("/js/bootstrap.min.js", "/js/bootstrap.min.js"));
        views.add(new MyView("/js/jquery-2.2.0.min.js", "/js/jquery-2.2.0.min.js"));
        views.add(new MyView("/js/scripts.js", "/js/scripts.js"));

        views.add(new MyView("/redirect:", "/redirect:"));
        views.add(new MyView("/index.html", "/index.html"));
        views.add(new MyView("/index", "/index.html"));
        views.add(new MyView("/", "/index.html"));

        views.add(new MyView("/user/login", "/user/login.html"));
        views.add(new MyView("/user/form", "/user/form.html"));
        views.add(new MyView("/user/list", "/user/list.html"));
        views.add(new MyView("/user/login_failed", "/user/login_failed.html"));
        views.add(new MyView("/user/profile", "/user/profile.html"));

        views.add(new MyView("/qna/form", "/qna/form.html"));
        views.add(new MyView("/qna/show", "/qna/show.html"));

        return new ViewResolver(views);
    }
}
