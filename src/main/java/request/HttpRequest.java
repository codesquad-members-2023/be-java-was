package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    private String method;
    private String url;
    private String httpVersion;
    private Map<String, String> params = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void setRequestLine(String requestLine) {
        StringTokenizer requestTokens = new StringTokenizer(requestLine, " ");
        method = requestTokens.nextToken();
        String urlWithParams = requestTokens.nextToken();
        httpVersion = requestTokens.nextToken();

        //쿼리 파라미터가 있는 경우와 없는 경우 구분
        if (urlWithParams.contains("?")) {
            StringTokenizer urlTokens = new StringTokenizer(urlWithParams, "\\?");
            url = urlTokens.nextToken();
            String queryString = urlTokens.nextToken();
            params = parseQueryParams(queryString);
            return ;
        }
        url = urlWithParams;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", params=" + params +
                '}';
    }

    private Map<String, String> parseQueryParams(String queryString) {
        Map<String, String> params = new HashMap<>();
        // &와 =로 분리
        String[] tokens = queryString.split("\\&");
        for (String token : tokens) {
            //Key Value로 분리하여 map에 저장
            StringTokenizer st = new StringTokenizer(token, "\\=");
            params.put(st.nextToken(), st.nextToken());
        }

        return params;
    }

    private Map<String, String> getRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String requestHeader;
        while (!(requestHeader = br.readLine()).equals("")) {
            log.debug("header : {}", requestHeader);
            String[] headerToken = requestHeader.split(":");
            if (headerToken.length == 2) {
                headers.put(headerToken[0], headerToken[1].trim());
            }
        }
        return headers;
    }

    public String getRequestBody(BufferedReader br) throws IOException {
        Map<String, String> headers = getRequestHeader(br);
        return IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
    }
}
