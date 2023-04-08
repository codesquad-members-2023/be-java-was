package util;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", ".js", "/static"),
    CSS("text/css", ".css","/static"),
    WOFF("font/woff", ".woff","/static"),
    ICO("image/avif",".ico","/templates"),
    PNG("image/png", ".png", "/static"),
    HTML("text/html;charset=utf-8", ".html","/templates");

    private String value;
    private String pattern;
    private String path;
    private String defaultPath = "src/main/resources";

    public String getValue(){
        return value;
    }

    public String getPath(){
        return defaultPath + path;
    }

    ContentType(String value, String pattern,String path) {
        this.value = value;
        this.pattern = pattern;
        this.path = path;
    }

    public static ContentType of (String path){
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.pattern))
                .findAny()
                .orElse(HTML);
    }


}
