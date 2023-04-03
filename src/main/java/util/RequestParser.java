package util;

public class RequestParser {
    public String getPath(String line) {
        return line.split(" ")[1];
    }
}
