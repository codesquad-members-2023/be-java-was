package util;

import java.util.Arrays;

public enum ResponseBody {
    CSS("text/css", "src/main/resources/static"),
    JS("application/javascript", "src/main/resources/static"),
    TTF("font/ttf", "src/main/resources/static"),
    WOF("font/woff", "src/main/resources/static"),
    PNG("image/png", "src/main/resources/static"),
    ICO("image/x-icon", "src/main/resources/templates"),
    BASIC("text/html", "src/main/resources/templates");

    private final String contentType;
    private final String path;

    ResponseBody(String contentType, String path) {
        this.contentType = contentType;
        this.path = path;
    }

    public static ResponseBody findResponseBody(String contentType) {
        return Arrays.stream(values())
                .filter(e -> contentType.equals(e.contentType))
                .findAny()
                .orElse(BASIC);
    }

    public String getPath() {
        return path;
    }
}
