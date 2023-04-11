package response;

public enum ContentsType {

    CSS("text/css;charset=utf-8", "static", ".*\\.css"),
    JS("application/javascript", "static", ".*\\.js"),
    FONTS("application/octet-stream", "static", ".*\\.woff$|.*\\.woff2$.*\\.woff3$"),
    PNG("image/png", "static", ".*\\.png"),
    ICO("image/avif", "templates", ".*\\.ico"),
    HTML("text/html;charset=utf-8", "templates", ".*\\.html");

    private static final String BASE_DIR = "src/main/resources/";
    private String contentType;
    private String locatedPath;
    private String identifier;

    ContentsType(String contentType, String locatedPath, String identifier) {
        this.contentType = contentType;
        this.identifier = identifier;
        this.locatedPath = BASE_DIR + locatedPath;
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
