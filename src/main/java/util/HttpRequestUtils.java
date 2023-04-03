package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String line){
        String[] splited = line.split(" ");
        String path = splited[1];
        logger.debug("request {} = " + path);
        return path;
    }

}
