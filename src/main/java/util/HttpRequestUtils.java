package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public String getStartLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public String getMethod(String startLine) {
        String method = getStatus(startLine, 0);
        logger.debug("request method: {}", method);
        return method;
    }

    public String getUrl(String startLine) {
        String path = getStatus(startLine, 1);

        // root 맵핑(index.html 으로)
        if (path.equals("/")) {
            path = "/index.html";
        }
        logger.debug("request path: {}", path);
        return path;
    }

    private String getStatus(String startLine, int num) {
        String[] splitLine = startLine.split(" ");
        return splitLine[num];
    }

    public Map<String, String> getRequestHeaders(BufferedReader br) {
        try {
            Map<String, String> headers = new HashMap<>();
            String line;
            logger.debug("----------RequestHeaders-START------------");
            while ((line = br.readLine()) != null && !line.equals("")) {
                logger.debug("requestHeader: {}", line);
                String[] headerTokens = line.split(": ");
                headers.put(headerTokens[0], headerTokens[1]);
            }
            logger.debug("----------RequestHeaders-END--------------");

            return headers;
        } catch (IOException e) {
            logger.error("error: occurred while reading the request header", e);
        }

        return null;
    }

    public String getRequestBody(BufferedReader br, int contentLength) {
        try {
            char[] charBuffer = new char[contentLength];
            br.read(charBuffer);

            return new String(charBuffer);
        } catch (IOException e) {
            logger.error("error: occurred while reading the request header", e);
        }

        return null;
    }

    public User joinWithPOST(String requestBody) {
        Map<String, String> params = ParseQueryUtils.parseQueryString(requestBody);
        return new User(decoding(params.get("userId")), decoding(params.get("password"))
                , decoding(params.get("name")), decoding(params.get("email")));
    }

    public String decoding(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
