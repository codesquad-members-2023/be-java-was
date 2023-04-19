package view.viewTemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static view.viewTemplate.TemplateConstans.UNLESS_TAG;

class TemplateParserTest {
    String sample;

    @BeforeEach
    void init() {
        
    }
    
    @Test
    public void getTagTest() throws Exception{
        //given
        sample = "                {{^user}}\n" +
                "                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>\n" +
                "                {{/^user}}";

        //when
        String tag = TemplateParser.getTag(sample, UNLESS_TAG);

        //then
        assertThat(tag).isEqualTo("{{^user}}");
    }

}
