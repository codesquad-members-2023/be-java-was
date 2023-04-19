package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static util.HttpParser.*;

public class HttpRequest {
    private final String requestLine;
    private final String uri;
    private final String method;
    private final String version;
    private final Map<String, String> queryParameter;
    private final Map<String, String> requestHeaders;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = parseRequestLine(br);
        this.uri = parseUri(requestLine);
        this.method = parseMethod(requestLine);
        this.version = parseVersion(requestLine);
        this.requestHeaders = parseRequestHeader(br);
        this.queryParameter = parseQueryParameter(requestLine);
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public String getQueryParameter(String key) {
        return queryParameter.get(key);
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public String getHeaderValue(String headerName) {
        return requestHeaders.get(headerName);
    }
}
