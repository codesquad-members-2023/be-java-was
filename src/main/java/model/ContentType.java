package model;

public enum ContentType {
    HTML("text/html", "templates", ".html"),
    ICO("image/*", "templates", ".ico"),
    CSS("text/css", "static", ".css"),
    JS("application/javascript", "static", ".js"),
    FONT("application/octet-stream", "static", "");

    private static final String BASE_PATH = "src/main/resources/";
    private final String contentType;
    private final String pathName;
    private final String extension;

    ContentType(String contentType, String pathName, String extension) {
        this.contentType = contentType;
        this.pathName = pathName;
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPathName() {
        return BASE_PATH + pathName;
    }

    public String getExtension() {
        return extension;
    }

    public static ContentType of(String url) {
        for (ContentType type : ContentType.values()) {
            if (url.endsWith(type.getExtension())) {
                return type;
            }
        }
        return FONT;
    }
}
