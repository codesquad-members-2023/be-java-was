package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String, String> params;

    public HttpRequest() {
        this.params = new HashMap<>();
    }

    public String startLine(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return br.readLine();
    }

    public String getUrl(String line) {
        String[] splited = line.split(" ");
        String path = splited[1];
        logger.debug("request {} = " + path);
        return path;
    }

    public String getMethod(String line){
        String[] splited = line.split(" ");
        String method = splited[0];
        return method;
    }

    public Map<String, String> parseQueryString(String url) {
        int index = url.indexOf("?");
        String queryString = url.substring(index + 1);
        String[] tokens = queryString.split("&");
        for(int i=0; i< tokens.length; i++){
            String[] token = tokens[i].split("=");
            params.put(token[0],token[1]);
        }
        return params;
    }

    public void addUser(Map<String, String> params){
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
    }
}
