package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTypeTest {

    @Test
    @DisplayName(".js가 들어왔을때 ContentType class의 of메소드에서 key값인 application/javascript를 제대로 가져오는지 확인")
    void of() {
        String url = "test.js";
        ContentType type = ContentType.of(url);
        Assertions.assertThat(type.value).isEqualTo("application/javascript");
    }
}
