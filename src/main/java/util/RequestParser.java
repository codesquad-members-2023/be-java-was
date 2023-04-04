package util;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public String getMethod(String line) {
        return line.split(" ")[0];
    }

    public String getUriPath(String line) {
        return line.split(" ")[1];
    }

    public String getPath(String line) {
        return getUriPath(line).split("\\?")[0];
    }

    public Map<String, String> getQueryParammeter(String line) {
        String[] parammeterQuery = getUriPath(line).split("\\?")[1].split("&");

        Map<String, String> parammeter = new HashMap<>();

        for (int i=0; i<parammeterQuery.length; i++) {
            String[] split = parammeterQuery[i].split("=");
            parammeter.put(split[0], split[1]);
        }

        return parammeter;
    }
}
