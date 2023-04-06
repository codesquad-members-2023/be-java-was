package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String firstLine) {
        String[] splitLine = firstLine.split(" ");
        String path = splitLine[1];

        // root 맵핑(index.html 으로)
        if (path.equals("/")) {
            path = "/index.html";
        }
        logger.debug("request path: {}", path);
        return path;
    }

    public static String getStartLine(BufferedReader br) {
        try {
            logger.debug("----------RequestHeader-START------------");
            String headerLine = br.readLine();
            String firstLine = headerLine;
            while (headerLine != null && !headerLine.equals("")) {
                logger.debug("requestHeader: {}", headerLine);
                headerLine = br.readLine();
            }
            logger.debug("----------RequestHeader-END--------------");
            return firstLine;
        } catch (IOException e) {
            logger.error("error: occurred while reading the request header", e);
        }

        return null;
    }

    public static String joinWithGET(String url) {
        int index = url.indexOf("?");
        String queryString = url.substring(index + 1);
        Map<String, String> params = ParseQueryUtils.parseQueryString(queryString);
        User user = new User(params.get("userId"), params.get("password")
                , URLDecoder.decode(params.get("name"), StandardCharsets.UTF_8), params.get("email").replace("%40", "@"));
        logger.debug("User: {}", user);

        return "/index.html";
    }
}
