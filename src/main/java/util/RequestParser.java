package util;

public class RequestParser {

    public static String[] separateUrls(String requestLine) {
        return requestLine.split(" ");
    }

    public static String[] parseQueryParameter(String resourceUrl) {
        return resourceUrl.split("\\?");
    }

}
