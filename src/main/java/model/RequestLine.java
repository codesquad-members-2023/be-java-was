package model;

public class RequestLine {
    private final String method;
    private final String URL;
    private final String queryString;
    private final String HTTPVersion;

    public RequestLine(String method, String URL, String queryString, String HTTPVersion) {
        this.method = method;
        this.URL = URL;
        this.queryString = queryString;
        this.HTTPVersion = HTTPVersion;
    }

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return URL;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getURLPath() {
        return URL.substring(URL.lastIndexOf("/"));
    }

    public String getHTTPVersion() {
        return HTTPVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", URL='" + URL + '\'' +
                ", queryString='" + queryString + '\'' +
                ", HTTPVersion='" + HTTPVersion + '\'' +
                '}';
    }
}
