package db;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import model.Session;
import model.User;

public class SessionDb {
    private static Map<String, Session> sessionMap = Maps.newHashMap();

    public static String addSessionedUser(String userId) {
        Session userSession = new Session();
        sessionMap.put(userId, userSession);
        return userSession.getSessionId();
    }

}
