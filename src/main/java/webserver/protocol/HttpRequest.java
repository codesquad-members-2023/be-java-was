package webserver.protocol;

import util.ProtocolParser;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final Method method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParameters;
    private final Map<String, String> headers;

    public HttpRequest(String requestLine, String headerStr) {
        this.method = ProtocolParser.parseMethod(requestLine);
        this.path = ProtocolParser.parsePath(requestLine);
        this.httpVersion = ProtocolParser.parseVersion(requestLine);
        this.queryParameters = ProtocolParser.parseQueryParameter(requestLine);
        this.headers = ProtocolParser.parseHeaders(headerStr);
    }

    public HttpRequest(String requestLine) {
        this.method = ProtocolParser.parseMethod(requestLine);
        this.path = ProtocolParser.parsePath(requestLine);
        this.httpVersion = ProtocolParser.parseVersion(requestLine);
        this.queryParameters = ProtocolParser.parseQueryParameter(requestLine);
        this.headers = new HashMap<>();
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }
    }

    public boolean isPath(String path) {
        return this.path.equals(path);
    }

    public String getVersion() {
        return httpVersion;
    }
}
