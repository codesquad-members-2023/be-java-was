package webserver.protocol.session;

import java.util.HashMap;
import java.util.Map;

public class SessionStore {
    private static Map<String, Session> sessions = new HashMap<>();

    public static synchronized void addSession(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Session findSessionById(String sessionId) {
        return sessions.get(sessionId);
    }

    public static synchronized void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
