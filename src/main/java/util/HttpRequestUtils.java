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

        // root 맵핑(index.html 으로)
        if (path.equals("/")) {
            path = "/index.html";
        }
        logger.debug("request path: {}", path);
        return path;
    }

    public static String getRequestHeader(BufferedReader br) {
        try {
            logger.debug("----------RequestHeader-START------------");
            String headerLine = br.readLine();
            String firstLine = headerLine;
            while (headerLine != null && !headerLine.equals("")) {
                logger.debug("requestHeader: {}", headerLine);
                headerLine = br.readLine();
            }
            logger.debug("----------RequestHeader-END--------------");
            return firstLine;
        } catch (IOException e) {
            logger.error("error: occurred while reading the request header", e);
        }

        return null;
    }
}
