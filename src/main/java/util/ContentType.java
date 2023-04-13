package util;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", ".js", "/static"),
    CSS("text/css", ".css","/static"),
    WOFF("font/woff", ".woff","/static"),
    ICO("image/avif",".ico","/templates"),
    PNG("image/png", ".png", "/static"),
    HTML("text/html;charset=utf-8", ".html","/templates");

    private String type;
    private String extension;
    private String path;
    private String defaultPath = "src/main/resources";

    public String getType(){
        return type;
    }

    public String getPath(){
        return defaultPath + path;
    }

    ContentType(String type, String extension,String path) {
        this.type = type;
        this.extension = extension;
        this.path = path;
    }

    public static ContentType of (String path){
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.extension))
                .findAny()
                .orElse(HTML);
    }


}
