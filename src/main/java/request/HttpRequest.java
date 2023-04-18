package request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import response.HttpHeaders;

public class HttpRequest {
    private String method;
    private String url;
    private String httpVersion;
    private Map<String, String> params = new HashMap<>();
    private HttpHeaders httpHeaders;
    private String body;

    /**
     * HTTP Request의 첫 줄을 인자로 받아 method, url, http 버전 정보를 파싱하여 저장합니다.
     * 쿼리 파라미터가 있는 경우 params에 key - value를 저장합니다.
     * @param requestLine
     */
    public void initRequestLine(String requestLine) {
        StringTokenizer requestTokens = new StringTokenizer(requestLine, " ");
        method = requestTokens.nextToken();
        String urlWithParams = requestTokens.nextToken();
        httpVersion = requestTokens.nextToken();

        //쿼리 파라미터가 있는 경우와 없는 경우 구분
        if (urlWithParams.contains("?")) {
            StringTokenizer urlTokens = new StringTokenizer(urlWithParams, "\\?");
            url = urlTokens.nextToken();
            String queryString = urlTokens.nextToken();
            params = HttpRequestUtils.parseQueryParams(queryString);
            return ;
        }
        url = urlWithParams;
    }

    public Optional<String> getSessionId() {
        return httpHeaders.getSessionId();
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", params=" + params +
                ", httpHeaders=" + httpHeaders +
                ", body='" + body + '\'' +
                '}';
    }
}
