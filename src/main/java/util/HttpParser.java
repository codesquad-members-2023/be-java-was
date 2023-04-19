package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * HttpParser 클래스는 http request 입력값을 파싱 기능을 수행합니다.
 */
public class HttpParser {
    private static final Logger log = LoggerFactory.getLogger(HttpParser.class);

    /**
     * http 메소드를 파싱하여 반환합니다.
     *
     * @param requestLine
     * @return http method (에시: GET, POST)
     */
    public static String parseMethod(String requestLine) {
        String method = requestLine.split(" ")[0];
        log.debug("method : {}", method);
        return method;
    }

    /**
     * request line을 파싱하여 반환합니다.
     * @param br
     * @return
     */
    public static String parseRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        log.debug("requestLine = {}", requestLine);
        return requestLine;
    }

    /**
     * 전체 URI를 파싱하여 반환합니다.
     *
     * @param requestLine
     * @return http URI (예시: /index.html, /user/create?id=core&password=123&email=core@naver.com)
     */
    public static String parseUri(String requestLine) {
        String uri = requestLine.split(" ")[1];
        log.debug("uri : {}", uri);
        return uri;
    }

    /**
     * uri중 request parameter를 제외한 경로만 파싱하여 반환합니다.
     *
     * @param requestLine
     * @return http URI (예시: /index.html, /user/create)
     */
    public static String parseUriPath(String requestLine) {
        String uriPath = parseUri(requestLine).split("\\?")[0];
        log.debug("uriPath : {}", uriPath);
        return uriPath;
    }

    /**
     * uri 루트 경로만 파싱하여 반환합니다.
     * @param requestLine
     * @return http root uri (예시: /user, /article)
     */
    public static String parseUriRootPath(String requestLine) {
        String uriPath = parseUriPath(requestLine);
        int index = uriPath.indexOf("/", 1);
        if (index == -1) {
            return uriPath;
        }
        String uriRootPath = uriPath.substring(0, index);
        log.debug("uriRootPath : {}", uriRootPath);
        return uriRootPath;
    }

    /**
     * http 버전을 파싱하여 반환합니다.
     *
     * @param requestLine
     * @return http version (예시: http/1.1)
     */
    public static String parseVersion(String requestLine) {
        String version = requestLine.split(" ")[2];
        log.debug("version : {}", version);
        return version;
    }

    /**
     * request header를 파싱하여 반환합니다.
     *
     * @param br BufferedReader 형식의 소켓으로 받아온 inputstream입니다.
     * @return request line을 제외한 나머지 헤더입니다.
     * @throws IOException
     */
    public static Map<String, String> parseRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> requestHeaders = new LinkedHashMap<>();
        String headerLine;
        while (!(headerLine = br.readLine()).equals("")) {
            log.debug("header : {}", headerLine);
            String[] tokens = headerLine.split(": ");
            requestHeaders.put(tokens[0], tokens[1]);
        }
        return requestHeaders;
    }

    /**
     * uri 쿼리 파라미터를 파싱하여 해시맵으로 반환합니다.
     *
     * @param requestLine
     * @return queryParameter (예시: [id: core], [password: 1234])
     */
    public static Map<String, String> parseQueryParameter(String requestLine) {
        String[] queryString = parseUri(requestLine).split("\\?");
        log.debug("queryString = {}", Arrays.toString(queryString));
        if (queryString.length == 1) {
            return new HashMap<>();
        }

        StringTokenizer st = new StringTokenizer(queryString[1], "&");
        Map<String, String> queryParameter = new HashMap<>();

        while (st.hasMoreTokens()) {
            String[] keyAndValue = st.nextToken().split("=");
            log.debug("queryParameter : {}", Arrays.toString(keyAndValue));
            String value = URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8); // 한글, 특수문자 URL 디코딩
            queryParameter.put(keyAndValue[0], value);
        }

        return queryParameter;
    }
}
