package util;

import db.Database;
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

    public static String getMethod(String startLine) {
        String method = getStatus(startLine, 0);
        logger.debug("request method: {}", method);
        return method;
    }

    public static String getUrl(String startLine) {
        String path = getStatus(startLine, 1);

        // root 맵핑(index.html 으로)
        if (path.equals("/")) {
            path = "/index.html";
        }
        logger.debug("request path: {}", path);
        return path;
    }

    private static String getStatus(String startLine, int num) {
        String[] splitLine = startLine.split(" ");
        return splitLine[num];
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
        User user = new User(decoding(params.get("userId")), decoding(params.get("password"))
                , decoding(params.get("name")), decoding(params.get("email")));
        Database.addUser(user);
        logger.debug("User: {}", user);

        return "/index.html";
    }

    public static String decoding(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
