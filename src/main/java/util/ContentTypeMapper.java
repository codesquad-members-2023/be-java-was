package util;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeMapper {

    private static Map<String, String> contentTypeList = new HashMap<>() {{
        put("html", "text/html");
        put("css", "text/css");
        put("js", "application/javascript");
        put("png", "image/png");
        put("ico", "image/x-icon");
    }};

    public static String getContentTypeByExtension(String extension) {
        return contentTypeList.get(extension);
    }
}
