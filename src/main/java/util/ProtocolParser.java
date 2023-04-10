package util;

import webserver.protocol.Method;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProtocolParser {
    /**
     * requestLine에서 메서드를 반환
     * @param requestLine
     * @return httpRequest method(GET, POST 등)
     */
    public static Method parseMethod(String requestLine) {
        return Method.of(requestLine.split(" ")[0]);
    }

    /**
     * requestLine에서 uri path를 반환
     * @param requestLine
     * @return httpRequest uriPath(예시 : /user/create?userId=user&password=1234)
     */
    private static String parseUriPath(String requestLine) {
        return requestLine.split(" ")[1];
    }

    /**
     * requestLine에서 path를 반환
     * @param requestLine
     * @return 요청 path(예시 : /user/create)
     */
    public static String parsePath(String requestLine) {
        return parseUriPath(requestLine).split("\\?")[0];
    }

    /**
     * requestLine에서 uri path를 반환
     * @param requestLine
     * @return http version(예시 : HTTP/1.1)
     */
    public static String parseVersion(String requestLine) {
        return requestLine.split(" ")[2];
    }

    /**
     * Get 요청시 path와 함께 요청되는 쿼리 파라미터 파싱
     * @param requestLine
     * @return Query parameter
     */
    public static Map<String, String> parseQueryParameter(String requestLine) {
        String[] queryString = parseUriPath(requestLine).split("\\?");  // TODO 회원가입시 쿼리 파라미터 없으면 예외가 발생해야 하지 않을까?

        if (queryString.length == 1) {
            return new HashMap<>();
        }

        return parseParameter(queryString[1]);
    }

    public static Map<String, String> parseParameter(String queryString) {
        if (queryString.equals("")) {
            return new HashMap<>();
        }
        String[] parammeterQuery = queryString.split("&");

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

    /**
     * 요청 header 파싱
     * @param requestLine
     * @return request header
     */
    public static Map<String, String> parseHeaders(String headerStr) {
        Map<String, String> headers = new HashMap<>();

        for (String line : headerStr.split("\n")) {
            int splitIndex = line.indexOf(":");
            headers.put(line.substring(0, splitIndex).trim(), line.substring(splitIndex+1).trim());
        }

        return headers;
    }
}
