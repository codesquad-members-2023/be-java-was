package util;

import controller.ArticleController;
import controller.Controller;
import db.ArticleDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ArticleService;
import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

import java.io.DataOutputStream;
import java.util.Collection;
import java.util.List;


class MyArgumentResolverTest {

    private static final String URL = "jdbc:mysql://127.0.01:3306/codesquad";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    MyArgumentResolver myArgumentResolver = new MyArgumentResolver();

    @Test
    @DisplayName("경로 변수가 주어진 요청이 올 때, 경로 변수를 파싱해야한다.")
    public void parsePathVariableTest() throws NoSuchMethodException, ClassNotFoundException {

        // given
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setPathInfo("/qna/show/123");
        httpRequest.setMethod("GET");
        HttpResponse httpResponse = new HttpResponse(new DataOutputStream(System.out));
        ModelAndView modelAndView = new ModelAndView();
        ArticleController articleController = new ArticleController(new ArticleService(new ArticleDatabase(URL, USERNAME, PASSWORD)));
        Collection<Controller> controllers = List.of(new ArticleController[]{articleController});
        MyHandlerMethod handler = new MyHandlerMethod(httpRequest, controllers);

        // when
        List<Object> result = myArgumentResolver.resolve(httpRequest, httpResponse, modelAndView, handler);

        // then
        int actualParameter = (int)result.get(0); // int 타입의 파라미터

        Assertions.assertThat(actualParameter).isEqualTo(123);


    }

}
