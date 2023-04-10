package util;

public enum ContentType {
    HTML("text/html", "src/main/resources/templates"),
    CSS("text/css", "src/main/resources/static"),
    JS("application/javascript", "src/main/resources/static"),
    FONT("application/octet-stream", "src/main/resources/static");

    private final String contentType;
    private final String pathName;

    ContentType(String contentType, String pathName) {
        this.contentType = contentType;
        this.pathName = pathName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPathName() {
        return pathName;
    }

    public static ContentType of(String url) {
        if (url.endsWith(".css")) {
            return CSS;
        } else if (url.endsWith(".js")) {
            return JS;
        } else if (url.startsWith("/fonts")) {
            return FONT;
        } else {
            return HTML;
        }
    }
}
