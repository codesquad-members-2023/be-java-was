package request;

public class Request {

    // HTTP 요청을 나타내는 클래스

    private String method;
    private String uri;
    private String version;

    public Request(String method, String uri, String version) {      // query가 있는 Request
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
