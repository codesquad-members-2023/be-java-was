package request;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String, String> params;
    private Map<String, String> headers;
    private String url;
    private String method;
    private String httpVersion;
    private StringTokenizer token;

    public HttpRequest(String line) {
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
        token = new StringTokenizer(line," ");
        method = token.nextToken();
        url = token.nextToken();
        httpVersion = token.nextToken();
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> addHeader(String header) {
        //String[] headerQuery = header.split("\n");

        //for(String line : headerQuery) {
            int parseIndex = header.indexOf(":");
            headers.put(header.substring(0,parseIndex).trim(), header.substring(parseIndex+1).trim());
        //}
        return headers;
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    // body
    public Map<String, String> addParam(String body) {
        String[] tokens = body.split("&");
        for (int i = 0; i < tokens.length; i++) {
            String[] token = tokens[i].split("=");
            params.put(token[0], token[1]);
        }
        return params;
    }

    public String getBody(String key){
        return params.get(key);
    }

}
