package response;

public enum ContentsType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css;charset=utf-8"),
    JS("application/javascript"),
    FONTS("application/octet-stream");

    private String contentType;

    ContentsType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
