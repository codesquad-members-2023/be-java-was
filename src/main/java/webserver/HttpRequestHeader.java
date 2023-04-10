package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private Map<String, String> httpRequestHeader;

    public HttpRequestHeader() {
        this.httpRequestHeader = new HashMap<>();
    }

    public void saveHeaderNameAndValue(String name, String value) {
        httpRequestHeader.put(name, value);
    }
}
