package session;

import servlet.HttpRequest;
import servlet.HttpResponse;

import java.util.Map;
import java.util.UUID;

public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "SID";

    private final Map<String, Object> sessionStore;

    public SessionManager(Map<String, Object> sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void createSession(Object value, HttpResponse httpResponse) {

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
    }

    public Object getSession(HttpRequest httpRequest) {
        Cookie sessionCookie = findCookie(httpRequest);

        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpRequest httpRequest) {
        Cookie sessionCookie = findCookie(httpRequest);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpRequest httpRequest) {
        return httpRequest.getCookies().stream()
                .filter(cookie -> cookie.getName().equals(SessionManager.SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }

}
