package servlet;

import session.Cookie;
import type.ContentType;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public static int SC_OK = 200;
    public static int SC_FOUND = 302;

    private ContentType contentType;
    private final String LOCATION = "Location";

    private final String REDIRECT = "redirect:";


    private Collection<Cookie> cookies;

    private String redirectURL;

    private String pathInfo;

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

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
        if (url.startsWith(REDIRECT)) {
            String replaceURL = url.replace(REDIRECT, "");
            this.statusCode = SC_FOUND;
            this.headers.put(LOCATION, replaceURL);
            this.redirectURL= replaceURL;
        }
    }

    public String getRedirectURL() {
        return this.redirectURL;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
