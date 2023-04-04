package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String line) {
        String[] splited = line.split(" ");
        String path = splited[1];
        logger.debug("request {} = " + path);
        return path;
    }

    public static String getMethod(String line){
        String[] splited = line.split(" ");
        String method = splited[0];
        return method;
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String,String> params = new HashMap<>();
        String[] tokens = queryString.split("&");
        for(int i=0; i< tokens.length; i++){
            String[] token = tokens[i].split("=");
            params.put(token[0],token[1]);
        }
        return params;
    }

}
