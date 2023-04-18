package webserver.protocol.request;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestBody {

    private String body;

    public String getBody() {
        return body;
    }

    private HttpRequestBody(String body) {
        this.body = body;
    }

    public static HttpRequestBody of(int length, BufferedReader br) throws IOException {
        if (length == 0) {
            return new HttpRequestBody(null);
        }

        char[] body = new char[length];
        br.read(body, 0, length);
        return new HttpRequestBody(String.valueOf(body));
    }

}
