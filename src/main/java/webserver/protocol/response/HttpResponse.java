package webserver.protocol.response;

import com.google.common.net.HttpHeaders;
import webserver.protocol.ContentType;

import java.nio.charset.StandardCharsets;

import static util.Constants.*;

public class HttpResponse {
    public static final String REDIRECT_KEY = "redirect:";
    private final String HTTP_VERSION = "HTTP/1.1";
    private StatusCode statusCode;
    private HttpResponseHeader httpResponseHeader;
    private String path;
    private byte[] body;

    private HttpResponse(StatusCode statusCode, HttpResponseHeader httpResponseHeader, String path, byte[] body) {
        this.statusCode = statusCode;
        this.httpResponseHeader = httpResponseHeader;
        this.path = path;
        this.body = body;
    }

    public void sendForward(byte[] body, ContentType viewType) {
        if (statusCode.equals(StatusCode.ACCEPTED)) {
            statusCode = StatusCode.OK;
        }
        this.body = body;
        httpResponseHeader.forward(viewType, body.length);
    }

    public void sendRedirect(String viewPath) {
        path = path.replace(REDIRECT_KEY, EMPTY);
        statusCode = StatusCode.FOUND;
        httpResponseHeader.redirect(path);
    }

    public HttpResponse setStatus(StatusCode newStateCode) {
        statusCode = newStateCode;
        return this;
    }

    public HttpResponse setCookie(String key, String value) {
        httpResponseHeader.setCookieSession(key, value);
        return this;
    }

    public HttpResponse setCookieExpired(String key) {
        httpResponseHeader.setCookieExpired(key);
        return this;
    }

    public String getCookie(){
        return httpResponseHeader.getHeader(HttpHeaders.SET_COOKIE);
    }

    /**
     * @return Response Message를 String으로 반환한다.
     */
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION).append(SPACE);
        sb.append(statusCode.toString()).append(NEW_LINE);

        sb.append(httpResponseHeader.toString());

        if (body.length >0) {
            sb.append(new String(body, StandardCharsets.UTF_8));
        }

        return sb.toString();
    }

    /**
     * @return Accepted 코드를 가진 response 새로운 객체
     */
    public static HttpResponse accepted () {
        HttpResponseHeader httpResponseHeader = HttpResponseHeader.create();
        return new HttpResponse(StatusCode.ACCEPTED, httpResponseHeader, BASE_PATH, new byte[0]);
    }
}
