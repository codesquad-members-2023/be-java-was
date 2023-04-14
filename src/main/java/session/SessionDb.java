package session;

import java.util.Map;

import com.google.common.collect.Maps;

import session.Session;

public class SessionDb {
    private static Map<String, Session> sessionMap = Maps.newHashMap();

    public static String addSessionedUser(String userId) {
        Session userSession = new Session();
        sessionMap.put(userId, userSession);
        return userSession.getSessionId();
    }

}
