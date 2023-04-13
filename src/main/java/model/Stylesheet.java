package model;

public class Stylesheet {
    private final String contentType;
    private final String pathName;

    public Stylesheet(String contentType, String pathName) {
        this.contentType = contentType;
        this.pathName = pathName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPathName() {
        return pathName;
    }
}
