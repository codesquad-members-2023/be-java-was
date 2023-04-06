package response;

public enum ContentsType {

    CSS("text/css;charset=utf-8", "src/main/resources/static", ".*\\.css"),
    JS("application/javascript", "src/main/resources/static", ".*\\.js"),
    FONTS("application/octet-stream", "src/main/resources/static", ".*\\.woff$|.*\\.woff2$.*\\.woff3$"),
    PNG("image/png", "src/main/resources/static", ".*\\.png"),
    ICO("image/avif", "src/main/resources/templates",".*\\.ico"),
    HTML("text/html;charset=utf-8", "src/main/resources/templates", ".*\\.html");

    private String contentType;
    private String locatedPath;
    private String identifier;

    ContentsType(String contentType, String locatedPath, String identifier) {
        this.contentType = contentType;
        this.identifier = identifier;
        this.locatedPath = locatedPath;
    }

    ContentsType(String contentType, String locatedPath) {
        this.contentType = contentType;
        this.locatedPath = locatedPath;
    }

    public String getContentType() {
        return contentType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLocatedPath() {
        return locatedPath;
    }
}
