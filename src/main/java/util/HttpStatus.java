package util;

public enum HttpStatus {
    OK(200, HttpStatusSeries.SUCCESS, "OK"),
    MOVED_TEMPORARILY(302, HttpStatusSeries.REDIRECT, "Moved Temporarily");


    private int value;
    private HttpStatusSeries httpStatusSeries;
    private String reason;

    HttpStatus(int value, HttpStatusSeries httpStatusSeries, String reason) {
        this.value = value;
        this.httpStatusSeries = httpStatusSeries;
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public HttpStatusSeries getHttpStatusSeries() {
        return httpStatusSeries;
    }

    public String getReason() {
        return reason;
    }
}
