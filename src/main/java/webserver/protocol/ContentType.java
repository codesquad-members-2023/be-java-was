package webserver.protocol;

import java.util.Arrays;

public enum ContentType {

    JS("application/javascript", ".js", "/static"),
    CSS("text/css;charset=utf-8", ".css", "/static"),
    FONT("font/woff", ".woff", "/static"),
    PNG("image/png", ".png", "/static"),
    ICO("image/avif", ".ico", "/templates"),
    HTML("text/html;charset=utf-8", ".html", "/templates");

    private String headValue;
    private String pattern;
    private String typePath;
    private final String CLASS_PATH = "src/main/resources";

    public String getHeadValue() {
        return headValue;
    }

    public String getTypeDirectory() {
        return CLASS_PATH + typePath;
    }

    ContentType(String headValue, String pattern, String typePath) {
        this.headValue = headValue;
        this.pattern = pattern;
        this.typePath = typePath;
    }

    public static ContentType of(String path) {
        return Arrays.stream(values())
                .filter(e->path.endsWith(e.pattern)).findAny()
                .orElse(HTML);
    }
}
