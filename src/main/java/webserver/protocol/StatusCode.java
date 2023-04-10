package webserver.protocol;

public enum StatusCode {
    OK(200, "200 OK"),
    FOUND(302, "302 Found"),
    NOT_FOUND(404, "404 Not Found");

    private int code;
    private String responseLine;

    StatusCode(int code, String responseLine) {
        this.code = code;
        this.responseLine = responseLine;
    }

    public String getResponseLine() {
        return responseLine;
    }
}
