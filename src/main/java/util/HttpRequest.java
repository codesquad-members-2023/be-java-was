package util;

public class HttpRequest {
    private String method;
    private String url;
    private String httpVersion;
    private Map<String, String> params = new HashMap<>();

    public HttpRequest(String requestLine) {
        String[] requestTokens = requestLine.split(" ");
        method = requestTokens[0];
        httpVersion = requestTokens[2];

        String urlWithParams = requestTokens[1];
        //쿼리 파라미터가 있는 경우와 없는 경우 구분
        if (urlWithParams.contains("?")) {
            url = urlWithParams.split("\\?")[0];
            queryString = urlWithParams.split("\\?")[1];
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

    public String getQueryString() {
        return queryString;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
