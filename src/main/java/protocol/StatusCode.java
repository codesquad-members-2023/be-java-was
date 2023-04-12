package protocol;

public enum StatusCode {
    OK(200, "200 OK"),
    FOUND(302, "302 Found"),
    BAD_REQUEST(400, "400 Bad Request"),
    NOT_FOUND(404, "404 Not Found"),
    UNAUTHORIZED(401, "401 Unauthorized");

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
