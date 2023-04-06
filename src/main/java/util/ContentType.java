package util;

import java.util.Arrays;
import java.util.Optional;

public enum ContentType {
    JS("application/javascript", ".js"),
    CSS("text/css", ".css"),
    WOFF("font/woff", ".woff"),
    TFT("font/tft", "tft"),
    ICO("image/x-icon", ".ico"),
    PNG("image/png", ".png"),
    HTML("text/html", ".html");

    String value;
    String pattern;

    public String getValue() {
        return value;
    }

    ContentType(String value, String pattern) {
        this.value = value;
        this.pattern = pattern;
    }

    public static ContentType findContentType(String path) {
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.pattern))
                .findAny()
                .orElse(HTML);
    }

}
