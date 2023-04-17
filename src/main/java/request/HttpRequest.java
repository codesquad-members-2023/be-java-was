package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SingletonContainer;
import webserver.ContentTypeParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class HttpRequest {

    private Logger log = LoggerFactory.getLogger(getClass());


    private String CONTENT_LENGTH = "Content-Length";
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.httpRequestLine = new HttpRequestLine(br);
        this.httpRequestHeader = new HttpRequestHeader(br);

        Optional<String> contentLengthHeader = Optional.ofNullable(getValueByNameInHeader(CONTENT_LENGTH));
        contentLengthHeader.ifPresent(value -> {
            int contentLength = Integer.parseInt(value);
            try {
                this.httpRequestBody = new HttpRequestBody(br, contentLength);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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

    public HashMap<String, String> getHttpRequestBody() {
        return httpRequestBody.getBody();
    }
}
