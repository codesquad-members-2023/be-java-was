package type;

public enum ContentType {

    CSS("text/css;charset=utf-8",  ".*\\.css"),
    JS("application/javascript",  ".*\\.js"),
    FONTS("application/octet-stream", ".*\\.woff$|.*\\.woff2$|.*\\.ttf"),
    PNG("image/png",  ".*\\.png"),
    ICO("image/avif", ".*\\.ico"),
    HTML("text/html;charset=utf-8", ".*\\.html");


    private String contentType;
    private String identifier;

    ContentType(String contentType, String identifier) {
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
