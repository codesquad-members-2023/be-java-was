package webserver.protocol;

import util.ProtocolParser;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String METHOD;
    private final String PATH;
    private final String HTTP_VERSION;
    private final Map<String, String> QUERY_PARAMETERS;
    private final Map<String, String> HEADERS;

    public HttpRequest(String requestLine, String headerStr) {
        this.METHOD = ProtocolParser.parseMethod(requestLine);
        this.PATH = ProtocolParser.parsePath(requestLine);
        this.HTTP_VERSION = ProtocolParser.parseVersion(requestLine);
        this.QUERY_PARAMETERS = ProtocolParser.parseQueryParameter(requestLine);
        this.HEADERS = ProtocolParser.parseHeaders(headerStr);
    }

    public HttpRequest(String requestLine) {
        this.METHOD = ProtocolParser.parseMethod(requestLine);
        this.PATH = ProtocolParser.parsePath(requestLine);
        this.HTTP_VERSION = ProtocolParser.parseVersion(requestLine);
        this.QUERY_PARAMETERS = ProtocolParser.parseQueryParameter(requestLine);
        this.HEADERS = new HashMap<>();
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getPATH() {
        return PATH;
    }

    public String getParameter(String key) {
        return QUERY_PARAMETERS.get(key);
    }

    public boolean isPath(String path) {
        return this.PATH.equals(path);
    }

    public String getVersion() {
        return HTTP_VERSION;
    }
}
