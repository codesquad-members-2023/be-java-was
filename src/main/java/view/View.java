package view;

import webserver.protocol.ContentType;

public class View {
    private String path;
    private ContentType contentType;

    private View(String path, ContentType contentType) {
        this.path = path;
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public static View from(String path) {
        ContentType type = ContentType.of(path);
        return new View(path, type);
    }
}
