package util;

public class HttpRequest {

    private String method;
    private String url;
    private String queryString;

    public HttpRequest(String line) {
        String[] splitHeader = line.split(" ");
        // uriPath = 전체 uri (쿼리 파라미터 분리 전)
        String uriPath = getURIPath(splitHeader);

        // 쿼리 파라미터 분리 후 url
        this.url = getPath(uriPath);
        this.method = splitHeader[0];
        this.queryString = getQueryParameters(uriPath);
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

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", queryString='" + queryString + '\'' +
                '}';
    }

    private String getURIPath(String[] splitHeader) {
        return splitHeader[1];
    }

    private String getPath(String uriPath) {
        return uriPath.split("\\?")[0];
    }

    private String getQueryParameters(String uriPath) {
        String[] split = uriPath.split("\\?");
        if (split.length > 1) {
            return split[1];
        }
        return "";
    }
}
