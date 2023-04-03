package util;

public class RequestParser {
    private final String PATH_REG = "/[.]*";
    public String getPath(String line) {
        String path = line.split(" ")[1];

        if (!path.matches(PATH_REG)) {
            throw new IllegalArgumentException("잘못된 경로입니다.");
        }

        return path;
    }
}
