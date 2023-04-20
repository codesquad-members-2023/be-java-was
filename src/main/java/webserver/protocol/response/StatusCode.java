package webserver.protocol.response;

import util.Constants;

public enum StatusCode {
    OK(200, "OK"),
    ACCEPTED(202, "Accepted"),  // 클라이언트의 요청은 정상적이나, 서버가 아직 요청을 완료하지 못했다.
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized");

    private int code;
    private String responseLine;

    StatusCode(int code, String responseLine) {
        this.code = code;
        this.responseLine = responseLine;
    }

    @Override
    public String toString() {
        return code + Constants.SPACE + responseLine;
    }
}
