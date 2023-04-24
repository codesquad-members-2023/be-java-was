package util;

import servlet.HttpRequest;
import session.Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpRequestParser {

    private final Integer METHOD_INDEX = 0;
    private final Integer PATH_INDEX = 1;
    private final Integer QUERY_INDEX = 2;

    private final Integer KEY_INDEX = 0;

    private final Integer VALUE_INDEX = 1;

    private final String COOKIE = "Cookie";

    public HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        HttpRequest request = new HttpRequest();

        parseRequestLine(request, bufferedReader);
        parseRequestHeaders(request, bufferedReader);
        parseRequestBody(request, bufferedReader);

        return request;
    }

    private void parseRequestLine(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        String[] requestLine = bufferedReader.readLine().split(" ");
        request.setMethod(requestLine[METHOD_INDEX]);
        set(request, requestLine[PATH_INDEX]);
        request.setVersion(requestLine[QUERY_INDEX]);
    }

    private void set(HttpRequest request, String pathInfo) {
        String decodedUrl = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);
        if (decodedUrl.contains("?")) {
            String[] pathAndQuery = decodedUrl.split("\\?");
            request.setPathInfo(pathAndQuery[0]);
            request.setQueryString(pathAndQuery[1]);
            return;
        }
        request.setPathInfo(decodedUrl);
    }

    private void parseRequestHeaders(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        String line = null;
        while (!(line = bufferedReader.readLine()).equals("")) {
            addRequestHeaders(request, requestHeaders, line);
        }
        request.requestHeaders(requestHeaders);
    }

    private void addRequestHeaders(HttpRequest request, Map<String, String> requestHeaders, String line) {
        String[] headerElements = line.split(":");
        String headerName = headerElements[KEY_INDEX].trim();
        String headerValue = headerElements[VALUE_INDEX].trim();
        if (headerName.equals(COOKIE)) {
            addCookies(request, headerValue);
            return;
        }
        requestHeaders.put(headerName, headerValue);
    }

    private void addCookies(HttpRequest request, String headerValue) {
        Collection<Cookie> cookieList = new ArrayList<>();
        String[] cookies = headerValue.split(";");
        for (String cookie : cookies) {
            String[] cookieElements = cookie.split("=");
            String cookieName = cookieElements[KEY_INDEX].trim();
            String cookieValue = cookieElements[VALUE_INDEX].trim();
            cookieList.add(new Cookie(cookieName, cookieValue));
        }
        request.setCookies(cookieList);
        return;
    }

    private void parseRequestBody(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return;
        }

        // TODO : 클린 코드가 절실한 상황!
        Map<String, String> requestBody = new LinkedHashMap<>();
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);

        String bodyOfHeader = URLDecoder.decode(new String(body), StandardCharsets.UTF_8);
        String[] bodyElement = bodyOfHeader.split("&");
        for (String pair : bodyElement) {
            String[] keyAndValue = pair.split("=");
            requestBody.put(keyAndValue[0], keyAndValue[1]);
        }
        request.setRequestBody(requestBody);
    }
}
