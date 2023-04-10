package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StylesheetUtilsTest {
    @Test
    @DisplayName("getContentType 메서드 테스트: CSS, JS, Font, Html의 Content-Type이 올바르게 반환되는지 검증")
    public void getContentTypeTest() throws Exception {
        // given
        String urlCSS = "/css/styles.css";
        String urlJs = "/js/bootstrap.min.js";
        String urlFont = "/fonts/glyphicons-halflings-regular.woff";
        String urlHtml = "/index.html";

        String expectedCSS = "text/css";
        String expectedJs = "application/javascript";
        String expectedFont = "application/octet-stream";
        String expectedHtml = "text/html";

        // when
        String paramCss = StylesheetUtils.getContentType(urlCSS);
        String paramJs = StylesheetUtils.getContentType(urlJs);
        String paramFont = StylesheetUtils.getContentType(urlFont);
        String paramHtml = StylesheetUtils.getContentType(urlHtml);

        // then
        assertThat(paramCss).isEqualTo(expectedCSS);
        assertThat(paramJs).isEqualTo(expectedJs);
        assertThat(paramFont).isEqualTo(expectedFont);
        assertThat(paramHtml).isEqualTo(expectedHtml);
    }

    @Test
    @DisplayName("getPathName 메서드 테스트: URL 경로에 따라 정확한 파일 경로가 반환되는지 검증")
    public void getPathNameTest() throws Exception {
        // given
        String urlCSS = "/css/styles.css";
        String urlJs = "/js/bootstrap.min.js";
        String urlFont = "/fonts/glyphicons-halflings-regular.woff";
        String urlHtml = "/index.html";

        String expectedStylesheet = "src/main/resources/static";
        String expectedHtml = "src/main/resources/templates";

        // when
        String paramCss = StylesheetUtils.getPathName(urlCSS);
        String paramJs = StylesheetUtils.getPathName(urlJs);
        String paramFont = StylesheetUtils.getPathName(urlFont);
        String paramHtml = StylesheetUtils.getPathName(urlHtml);

        // then
        assertThat(paramCss).isEqualTo(expectedStylesheet);
        assertThat(paramJs).isEqualTo(expectedStylesheet);
        assertThat(paramFont).isEqualTo(expectedStylesheet);
        assertThat(paramHtml).isEqualTo(expectedHtml);
    }
}