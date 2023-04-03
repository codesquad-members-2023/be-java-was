package util;

public class RequestParser {

    String requestLine;

    public RequestParser(String requestLine) {
        this.requestLine = requestLine;
    }

    public String[] separateUrls() {
        return requestLine.split(" ");
    }

    private String[] parseQueryParameter(String resourceUrl) {
        return resourceUrl.split("\\?");
    }

}
