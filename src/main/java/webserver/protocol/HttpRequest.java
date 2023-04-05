package webserver.protocol;

import util.ProtocolParser;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParameter;

    public HttpRequest(String requestLine) {
        this.method = ProtocolParser.parseMethod(requestLine);
        this.path = ProtocolParser.parsePath(requestLine);
        this.httpVersion = ProtocolParser.parseVersion(requestLine);
        this.queryParameter = ProtocolParser.parseQueryParammeter(requestLine);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameter() {
        return queryParameter;
    }

    public String getParameter(String key) {
        return queryParameter.get(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
