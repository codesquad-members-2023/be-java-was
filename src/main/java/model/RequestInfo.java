package model;

import java.util.Map;

public class RequestInfo {
    private final String method;
    private final String url;
    private final Map<String, String> headers;

    public RequestInfo(String method, String url, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.headers = headers;
    }

    public boolean comparingMethod(String method) {
        return this.method.equals(method);
    }

    public String getHeaderData(String header) {
        return this.headers.get(header);
    }

    public String getUrl() {
        return url;
    }
}
