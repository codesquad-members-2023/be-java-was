package model;

import java.util.UUID;

public class Session {
    public Session() {
        this.sessionId = UUID.randomUUID().toString();
    }

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }
}
