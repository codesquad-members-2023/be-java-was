package servlet;

import session.Cookie;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public static int SC_OK = 200;
    public static int SC_CREATE  = 201;
    public static int SC_FOUND = 302;
    public static int SC_SEE_OTHER = 303;
    public static int SC_UNAUTHORIZED = 401;
    public static int SC_NOT_FOUND = 404;

    private Collection<Cookie> cookies;

    private String redirectURL;

    private int statusCode;

    private Map<String, String> headers;

    private OutputStream outputStream;



    public HttpResponse(OutputStream outputStream) {
        this.headers = new HashMap<>();
        this.cookies = new ArrayList<>();
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public Collection<Cookie> getCookies() {
        return this.cookies;
    }

    public boolean containHeader(String name) {
        return headers.containsKey(name);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHead(String name) {
        return headers.get(name);
    }

    public void setStatus(int sc) {
        this.statusCode = sc;
    }

    public int getStatus() {
        return this.statusCode;
    }

    public Collection<String> getHeadersName() {
        return new ArrayList<>(headers.keySet());
    }

    public void sendRedirect(String url) {
        // TODO : 리다이렉트에도 여러 종류가 있다!! (하지만 디폴트로 302는 나쁘지 않은 뜻?)
        this.statusCode = SC_FOUND;
        this.redirectURL = url;
    }

    public String getRedirectURL() {
        return this.redirectURL;
    }
}
