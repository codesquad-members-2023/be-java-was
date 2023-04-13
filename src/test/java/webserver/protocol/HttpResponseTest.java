package webserver.protocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.DataOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HttpResponseTest {

    @Mock
    HttpRequest httpRequest;
    @Mock
    DataOutputStream dos;
    HttpResponse httpResponse;

    @BeforeEach
    void init() {
        httpRequest = mock(HttpRequest.class);
        dos = mock(DataOutputStream.class);
        httpResponse = new HttpResponse(dos);
    }
    
    @Test
    @DisplayName("setcookie : 쿠키가 저장되는지 테스트")
    public void setCookieTest() throws Exception{
        httpResponse.setCookie("name", "test");
        assertThat(httpResponse.getCookie()).isEqualTo("name=test");
    }

    @Test
    @DisplayName("setcookie : 쿠키가 연속으로 저장되는지 테스트")
    public void setCookieChainTest() throws Exception{
        httpResponse.setCookie("name", "test")
                .setCookie("Max-Age","14400")
                .setCookie("Path", "/");
        assertThat(httpResponse.getCookie()).isEqualTo("name=test; Max-Age=14400; Path=/");
    }
}
