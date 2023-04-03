package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String firstLine) {
        String[] splitLine = firstLine.split(" ");
        String path = splitLine[1];
        logger.debug("request path: {}", path);
        return path;
    }
}
