package util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProtocolParser {
    public static String parseMethod(String requestLine) {
        return requestLine.split(" ")[0];
    }

    private static String parseUriPath(String requestLine) {
        return requestLine.split(" ")[1];
    }

    public static String parsePath(String requestLine) {
        return parseUriPath(requestLine).split("\\?")[0];
    }

    public static String parseVersion(String requestLine) {
        return requestLine.split(" ")[2];
    }

    public static Map<String, String> parseQueryParammeter(String requestLine) {
        String[] queryString = parseUriPath(requestLine).split("\\?");
        if (queryString.length == 1) {
            return new HashMap<>();
        }

        String[] parammeterQuery = queryString[1].split("&");

        Map<String, String> parammeter = new HashMap<>();

        for (int i=0; i<parammeterQuery.length; i++) {
            String decodedQuery = decode(parammeterQuery[i]);
            String[] split = decodedQuery.split("=");
            parammeter.put(split[0], split[1]);
        }

        return parammeter;
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
