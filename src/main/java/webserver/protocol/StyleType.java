package webserver.protocol;

import java.util.Arrays;
import java.util.Optional;

public enum StyleType {
    JS("application/javascript", ".js"),
    CSS("text/css", ".css"),
    FONT("text/css", ".woff");

    String value;
    String pattern;

    public String getValue() {
        return value;
    }

    StyleType(String value, String pattern) {
        this.value = value;
        this.pattern = pattern;
    }

    public static Optional<StyleType> anyMatchStyle(String path) {
        return Arrays.stream(values()).filter(e->path.endsWith(e.pattern)).findAny();
    }

}
