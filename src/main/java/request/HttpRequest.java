package request;

import webserver.ContentTypeParser;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;

    public HttpRequest(BufferedReader br) throws IOException {
        this.httpRequestLine = new HttpRequestLine(br);
        this.httpRequestHeader = new HttpRequestHeader(br);
    }

    public String getValueByNameInHeader(String name) {
        return httpRequestHeader.getValueByNameInHeader(name);
    }

    public String getValueByNameInRequestLine(String name) {
        return httpRequestLine.getValueByNameInRequestLine(name);
    }

    public String getExtension() {
        return httpRequestLine.getExtension();
    }
}
