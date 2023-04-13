package webserver.protocol.request;

import util.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

public class HttpRequestHeader {
    private Map<String, String> headers;

    public HttpRequestHeader() {
        this.headers = new HashMap<>();
    }

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    private static String readHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String header;
        while (!((header = br.readLine()).equals(""))) {
            sb.append(header).append("\n");
        }
        return sb.toString();
    }

    public static HttpRequestHeader from(BufferedReader br) throws IOException {
        return new HttpRequestHeader(ProtocolParser.parseHeaders(readHeader(br)));
    }

    public int getContentLength() {
        String length;
        if ((length = headers.get(CONTENT_LENGTH))==null) {
            return 0;
        }
        return Integer.parseInt(length);
    }
}
