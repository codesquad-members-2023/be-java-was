package util;

public enum HttpStatus {
    OK(200, "OK"),
    MOVED_TEMPORARILY(302, "Moved Temporarily");


    private int value;
    private String reason;

    HttpStatus(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
