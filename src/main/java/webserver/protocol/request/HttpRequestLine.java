package webserver.protocol.request;

import util.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequestLine {
    private Method method;
    private String urlPath;
    private String httpVersion;
    private Map<String, String> queryParameters;

    public Method getMethod() {
        return method;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public HttpRequestLine(Method method, String urlPath, String httpVersion, Map<String, String> queryParameters) {
        this.method = method;
        this.urlPath = urlPath;
        this.httpVersion = httpVersion;
        this.queryParameters = queryParameters;
    }

    public static HttpRequestLine from (BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        return from(requestLine);
    }

    public static HttpRequestLine from (String requestLine) {
        Method method = ProtocolParser.parseMethod(requestLine);
        String path = ProtocolParser.parsePath(requestLine);
        String httpVersion = ProtocolParser.parseVersion(requestLine);
        Map<String, String> queryParameters = ProtocolParser.parseQueryParameter(requestLine);
        return new HttpRequestLine(method, path, httpVersion, queryParameters);
    }

    public String getQueryParameter(String key) {
        return queryParameters.get(key);
    }

    public boolean isPath(String path) {
        return urlPath.equals(path);
    }
}
