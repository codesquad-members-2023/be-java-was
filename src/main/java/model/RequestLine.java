package model;

public class RequestLine {
    private final String method;
    private final String URL;
    private final String HTTPVersion;

    public RequestLine(String method, String URL, String HTTPVersion) {
        this.method = method;
        this.URL = URL;
        this.HTTPVersion = HTTPVersion;
    }

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return URL;
    }

    public String getURLPath() {
        return URL.substring(URL.lastIndexOf("/"));
    }

    public String getHTTPVersion() {
        return HTTPVersion;
    }
}
