package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public static int SC_OK = 200;
    public static int SC_CREATE  = 201;
    public static int SC_FOUND = 302;
    public static int SC_NOT_FOUND = 404;

    private String redirectURL;

    private int statusCode;

    private Map<String, String> headers;


    public HttpResponse() {
        this.headers = new HashMap<>();

        // TODO : 처음부터 OK 상태가 맞는지 나중에 다시 한번 생각해보기
        statusCode = SC_OK;
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
