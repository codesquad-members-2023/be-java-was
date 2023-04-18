package view;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.protocol.request.HttpRequest;
import webserver.protocol.response.HttpResponse;

import java.io.DataOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverTest {
    Logger logger = LoggerFactory.getLogger(ViewResolverTest.class);
    ViewResolver viewResolver;
    @Mock
    Model model;
    @Mock
    HttpRequest httpRequest;
    @Mock
    HttpResponse httpResponse;

    @BeforeEach
    void init() {
        viewResolver = new ViewResolver(new DataOutputStream(System.out));
        model = Model.create();
        model.addObject("user", new User("member", "1234", "이린", "iirin@naver.com"));
    }
    
    @Test
    @DisplayName("객체의 메서드 이름으로 값을 불러올 수 있다.")
    public void buildModelTest() throws Exception{
        //given
        String message = "{{user.getUserId}}";
        
        //when
        String result = ViewTemplate.renderModel(message, model);

        //then
        logger.debug(result);
        assertThat(result).isEqualTo("member");
    }

}
