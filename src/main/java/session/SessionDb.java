package session;

import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class SessionDb {
    private static Map<Session, User> sessionMap = Maps.newHashMap();

    public static String addSessionedUser(User user) {
        Session userSession = new Session();
        sessionMap.put(userSession, user);
        return userSession.getSessionId();
    }

}
