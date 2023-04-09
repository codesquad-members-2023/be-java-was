package utility;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtility {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtility.class);

    public static String getUrl(String firstLine) {
        String[] splittedRequestHeader = firstLine.split(" ");
        String method = splittedRequestHeader[0];
        String path = splittedRequestHeader[1];
        logger.debug(">> request method: {}", method);
        logger.debug(">> request path: {}", path);
        if (path.equals("/")) {
            path += "index.html";
        }
        return path;
    }

    /**
     * @param queryString queryString은 URL에서 ? 이후에 전달되는 name = value
     */
    public static Map<String, String> parseQueryString(String queryString) {
        logger.debug("queryString: {}", queryString);
        if (Strings.isNullOrEmpty(queryString)) {
            return Maps.newHashMap();
        }
        Map<String, String> parsedKeyValue = new HashMap<>();       // 쿼리문을 파싱하여 값을 저장 할 HashMap 생성
        String[] tokens = queryString.split("&");
        for (String token : tokens) {
            String[] splittedToken = token.split("=");
            parsedKeyValue.put(splittedToken[0], splittedToken[1]);
        }

        return parsedKeyValue;
    }

}
