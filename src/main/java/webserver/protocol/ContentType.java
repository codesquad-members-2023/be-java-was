package webserver.protocol;

import java.util.Arrays;

public enum ContentType {

    JS("application/javascript", ".js", "/static"),
    CSS("text/css", ".css", "/static"),
    FONT_WOFF("font/woff", ".woff", "/static"),
    FONT_TTF("font/ttf", ".ttf", "/static"),
    PNG("image/png", ".png", "/static"),
    ICO("image/avif", ".ico", "/templates"),
    HTML("text/html", ".html", "/templates");

    private final String RESOURCE_PATH = "src/main/resources";
    private String headValue;
    private String pattern;
    private String typePath;

    public String getHeadValue() {
        return headValue;
    }

    public String getTypeDirectory() {
        return RESOURCE_PATH + typePath;
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
