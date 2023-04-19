package webserver.protocol.request;

import util.ProtocolParser;
import webserver.protocol.session.SessionStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static webserver.RequestHandler.logger;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpRequestHeader headers;
    private HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestLine requestLine, HttpRequestHeader headers, HttpRequestBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.httpRequestBody = body;
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public String getUrlPath() {
        return requestLine.getUrlPath();
    }

    public String getQueryParameter(String key) {
        return requestLine.getQueryParameter(key);
    }

    public boolean isPath(String path) {
        return requestLine.isPath(path);
    }

    public String getBody() {
        return httpRequestBody.getBody();
    }

    public String getSessionKey() {
        return ProtocolParser.parseSession(headers.getCookie());
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestLine httpRequestLine = HttpRequestLine.from(br);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.from(br);
        HttpRequestBody httpRequestBody = HttpRequestBody.of(httpRequestHeader.getContentLength(), br);
        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    public boolean isSessionValid() {
        return SessionStore.isPresent(getSessionKey());
    }

    public Object getSession() {
        return SessionStore.findSessionById(getSessionKey()).getValue();
    }
}
