package webserver.protocol;

import util.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private Method method;
    private String path;
    private String httpVersion;
    private Map<String, String> queryParameters;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(Method method, String path, String httpVersion, Map<String, String> queryParameters, Map<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryParameters = queryParameters;
        this.headers = headers;
        this.body = body;
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

    public String getHeader(String key) {
        return headers.get(key);
    }

    public boolean isPath(String path) {
        return this.path.equals(path);
    }

    public boolean finalPath(String path) {
        return this.path.endsWith(path);
    }

    public String getVersion() {
        return httpVersion;
    }

    public String getBody() {
        return body;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String requestLine = br.readLine();

        Method method = ProtocolParser.parseMethod(requestLine);
        String path = ProtocolParser.parsePath(requestLine);
        String httpVersion = ProtocolParser.parseVersion(requestLine);
        Map<String, String> queryParameters = ProtocolParser.parseQueryParameter(requestLine);
        Map<String, String> headers = ProtocolParser.parseHeaders(readHeader(br));
        String body = readBody(headers.get("Content-Length"), br);

        return new HttpRequest(method, path, httpVersion, queryParameters, headers, body);
    }

    private static String readBody(String length, BufferedReader br) throws IOException {
        if (length==null) {
            return null;
        }
        int bodyLength = Integer.parseInt(length);
        char[] body = new char[bodyLength];
        br.read(body, 0, bodyLength);
        return String.valueOf(body);
    }

    private static String readHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String header;
        while (!((header = br.readLine()).equals(""))) {
            sb.append(header).append("\n");
        }
        return sb.toString();
    }
}
