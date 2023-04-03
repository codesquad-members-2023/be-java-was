package model;

public class RequestHeader {
    private final String headerMessage;

    public RequestHeader(String headerMessage) {
        this.headerMessage = headerMessage;
    }

    public String getHeaderMessage() {
        return headerMessage;
    }
}
