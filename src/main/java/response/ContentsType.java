package response;

public enum ContentsType {

    CSS("text/css;charset=utf-8", ".*\\.css"),
    JS("application/javascript", ".*\\.js"),
    FONTS("application/octet-stream", ".*\\.woff$|.*\\.woff2$.*\\.woff3$"),
    PNG("image/png", ".*\\.png"),
    ICO("image/avif", ".*\\.ico"),
    HTML("text/html;charset=utf-8", ".*\\.html");

    private String contentType;
    private String identifier;

    ContentsType(String contentType, String identifier) {
        this.contentType = contentType;
        this.identifier = identifier;
    }

    public String getContentType() {
        return contentType;
    }

    public String getIdentifier() {
        return identifier;
    }
}
