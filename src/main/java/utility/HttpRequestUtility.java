package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtility {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtility.class);

    public static String getUrl(String firstLine) {
        String[] splittedRequestHeader = firstLine.split(" ");
        String path = splittedRequestHeader[1];
        logger.debug("request path: {}", path);
        return path;
    }

}
