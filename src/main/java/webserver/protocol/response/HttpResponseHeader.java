package webserver.protocol.response;

import com.google.common.net.HttpHeaders;
import webserver.protocol.ContentType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static util.Constants.*;

public class HttpResponseHeader {
    private final String SEPARATOR = ": ";
    private Map<String, String> headers;
    private Map<String, Cookie> cookies;

    public HttpResponseHeader(Map<String, String> headers, Map<String, Cookie> cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    private void setHeader(String headerKey, String value) {
        headers.put(headerKey, value);
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public void setCookieSession(String cookieKey, String value) {
        Cookie cookie = new Cookie(cookieKey, value);
        cookie.setDomain(DOMAIN);
        cookie.setPath(BASE_PATH);
        cookies.put(cookieKey, cookie);
    }

    public void setCookieExpired(String cookieKey) {
        Cookie cookie = new Cookie(cookieKey, "logout");
        cookie.setExpires(LocalDateTime.now());
    }

    public String getSetCookie () {
        return headers.get(HttpHeaders.SET_COOKIE);
    }

    public String getCookie(String key) {
        return cookies.get(key).toString();
    }

    public void forward(ContentType type, int length) {
        headers.put(HttpHeaders.CONTENT_TYPE, type.getHeadValue());
        headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
    }

    public void redirect(String path) {
        headers.put(HttpHeaders.LOCATION, path);
    }

    public static HttpResponseHeader create() {
        return new HttpResponseHeader(new HashMap<>(), new HashMap<>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(SEPARATOR).append(entry.getValue()).append(NEW_LINE);
        }

        if (!cookies.isEmpty()) {
            sb.append(writeCookie());
        }
        sb.append(NEW_LINE);

        return sb.toString();
    }

    private String writeCookie() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpHeaders.SET_COOKIE).append(SEPARATOR);

        for (Map.Entry<String, Cookie> entry : cookies.entrySet()) {
            sb.append(entry.getValue().toString()).append(SPACE);
        }

        return sb.toString().trim();
    }
}
