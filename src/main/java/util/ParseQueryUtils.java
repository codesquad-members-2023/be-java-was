package util;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParseQueryUtils {
    public static Map<String, String> parseQueryString(String queryString) {
        if (Strings.isNullOrEmpty(queryString)) {
            return Maps.newHashMap();
        }

        String[] tokens = queryString.split("&");
        return Arrays.stream(tokens)
                .map(ParseQueryUtils::getKeyValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static Pair getKeyValue(String keyValue) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split("=");
        // tokens 예외상황 고려
        if (tokens.length != 2) {
            return null;
        }

        return new Pair(tokens[0], tokens[1]);
    }
}
