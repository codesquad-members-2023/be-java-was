package util;

public enum ResponseStatus {
    OK("200", "OK"),
    FOUND("302", "FOUND");

    private String statusCode;
    private String statusMessage;

    ResponseStatus(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
