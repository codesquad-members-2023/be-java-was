package webserver;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", ".js"),
    CSS("text/css", ".css"),
    WOFF("font/woff", ".woff"),
    HTML("text/html;charset=utf-8", ".html");

    String value;
    String pattern;

    public String getPattern(){
        return value;
    }

    ContentType(String value, String pattern) {
        this.value = value;
        this.pattern = pattern;
    }

    public static ContentType of (String path){
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.pattern))
                .findAny()
                .orElse(HTML);
    }


}
