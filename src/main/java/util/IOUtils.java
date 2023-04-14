package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class IOUtils {
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        String input = String.copyValueOf(body);
        return URLDecoder.decode(input, StandardCharsets.UTF_8);
    }
}
