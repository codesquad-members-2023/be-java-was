package util;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static Map<String, String> parseQueryString(String queryString) {
        if (Strings.isNullOrEmpty(queryString)) {
            return Maps.newHashMap();
        }

        String[] tokens = queryString.split("&");
        return Arrays.stream(tokens)
                .map(HttpRequestUtils::getKeyValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static Pair getKeyValue(String keyValue) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split("=");
        if (tokens.length != 2) {
            return null;
        }
        if (tokens[1].contains(".com")) {
            tokens[1] = tokens[1].replace("%", "@");
        }

        return new Pair(tokens[0], tokens[1]);
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
