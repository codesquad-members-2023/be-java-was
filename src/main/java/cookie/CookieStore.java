package cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CookieStore {

    private static Map<String, Cookie> cookieStore = new ConcurrentHashMap<>();

    public void saveCookie(Cookie cookie) {
        cookieStore.put(cookie.getUuid(), cookie);
    }

    public static Optional<Cookie> findCookieByUUID(String uuid) {
        return findAll().stream()
                .filter(cookie -> cookie.getUuid().equals(uuid))
                .findAny();
    }

    private static List<Cookie> findAll() {
        return new ArrayList<>(cookieStore.values());
    }
}
