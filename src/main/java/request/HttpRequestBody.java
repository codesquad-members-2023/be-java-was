package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequestBody {

    private Logger log = LoggerFactory.getLogger(getClass());
    private HashMap<String, String> body;

    public HttpRequestBody(BufferedReader br, int contentLength) throws IOException {
        body = new HashMap<>();
        char[] bodyContents = new char[contentLength];
        br.read(bodyContents, 0, contentLength);

        String bodyOfHeader = URLDecoder.decode(String.valueOf(bodyContents), StandardCharsets.UTF_8);
        String[] bodyData = bodyOfHeader.split("&");

        for (String pair : bodyData) {
            String[] keyAndValue = pair.split("=");
            body.put(keyAndValue[0], keyAndValue[1]);
            log.info("body data key = [{}], value = [{}]", keyAndValue[0], keyAndValue[1]);
        }

    }
}
