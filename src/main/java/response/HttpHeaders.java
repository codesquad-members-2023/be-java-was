package response;

import java.util.Map;

public class HttpHeaders {

    private Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuffer headerString = new StringBuffer();
        headers.entrySet().stream()
            .forEach(e -> headerString.append(e.getKey()).append(": ").append(e.getValue())
                .append("\r\n"));

        return headerString.append("\r\n").toString();
    }
}
