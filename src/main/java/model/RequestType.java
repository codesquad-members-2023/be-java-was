package model;

import java.util.stream.Stream;

public enum RequestType {

    HTML(".html", "text/html;charset=utf-8", "src/main/resources/templates"),
    CSS(".css", "text/css", "src/main/resources/static"),
    JS(".js", "application/javascript", "src/main/resources/static"),
    FONTS(".woff", "application/octet-stream", "src/main/resources/static"),
    PNG(".png", "image/png", "src/main/resources/static"),
    ICO(".ico", "image/avif", "src/main/resources/static");

    public String extension;
    public String contentType;

    public String basePath;

    RequestType(String extension, String contentType, String path) {
        this.extension = extension;
        this.contentType = contentType;
        this.basePath = path;
    }

    public static RequestType of(String url) {
        return Stream.of(values())
                .filter(type -> url.endsWith(type.extension))
                .findAny()
                .orElse(HTML);
    }

    public String getAbsolutePath(String url) {
        if (url.equals("/")) {
            return this.basePath + "/index.html";
        }

        return this.basePath + url;
    }

    public String getContentType() {
        return contentType;
    }
}
