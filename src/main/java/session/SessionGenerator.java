package session;

import java.util.UUID;

public class SessionGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
