package util;

public class stylesheetUtils {
    public static String getContentType(String url) {
        if (url.endsWith(".css")) {
            return "text/css";
        } else if (url.endsWith(".js")) {
            return "application/javascript";
        } else if (url.startsWith("/fonts")) {
            return "application/octet-stream";
        } else {
            return "text/html";
        }
    }

    public static String getPathName(String url) {
        if (url.endsWith(".css") || url.endsWith(".js") || url.startsWith("/fonts")) {
            return "src/main/resources/static";
        } else {
            return "src/main/resources/templates";
        }
    }
}
