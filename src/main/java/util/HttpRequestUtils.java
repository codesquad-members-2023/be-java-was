package util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestUtils {
    public static Map<String, String> parseQueryParams(HttpRequest httpRequest) {
        Map<String, String> params = new HashMap<>();
        // &와 =로 분리
        String[] tokens = httpRequest.getQueryString().split("\\&");
        for (String token : tokens) {
            //Key Value로 분리하여 map에 저장
            StringTokenizer st = new StringTokenizer(token, "\\=");
            params.put(st.nextToken(), st.nextToken());
        }

        return params;
    }
}
