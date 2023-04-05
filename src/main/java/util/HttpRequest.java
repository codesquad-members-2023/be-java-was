package util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String url;
    private String httpVersion;
    private Map<String, String> params = new HashMap<>();

    /**
     * HTTP Request의 첫 줄을 인자로 받아 method, url, http 버전 정보를 파싱하여 저장합니다.
     * 쿼리 파라미터가 있는 경우 params에 key - value를 저장합니다.
     * @param requestLine
     */
    public void init(String requestLine) {
        String[] requestTokens = requestLine.split(" ");
        method = requestTokens[0];
        httpVersion = requestTokens[2];

        String urlWithParams = requestTokens[1];
        //쿼리 파라미터가 있는 경우와 없는 경우 구분
        if (urlWithParams.contains("?")) {
            url = urlWithParams.split("\\?")[0];
            String queryString = urlWithParams.split("\\?")[1];
            params = HttpRequestUtils.parseQueryParams(queryString);
            return ;
        }
        url = urlWithParams;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
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
                ", params=" + params +
                '}';
    }
}
