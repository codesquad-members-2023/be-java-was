package protocol;

import java.util.Arrays;

public enum Method {
    GET, POST;

    public static Method of(String s) {
        return Arrays.stream(values()).filter(e -> e.name().equals(s))
                .findAny()
                .orElseThrow(() -> new RuntimeException("지원하지 않는 요청입니다."));
    }

    public boolean equals(String methodname) {
        return name().equals(methodname);
    }
}
