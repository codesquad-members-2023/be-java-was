package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String firstLine) {
        String[] splitLine = firstLine.split(" ");
        String path = splitLine[1];
        logger.debug("request path: {}", path);
        return path;
    }

    public static void getAllRequest(String line, BufferedReader br) throws IOException {
        while(!line.equals("")){
            logger.debug("request: {}", line);
            line = br.readLine();
        }
    }
}
