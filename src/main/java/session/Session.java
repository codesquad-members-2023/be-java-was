package session;

import java.util.UUID;

public class Session<T> {

    private String id;
    private T value;

    public String getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

    public Session(T value) {
        this.id = setSessionId();
        this.value = value;
    }

    private String setSessionId() {
        String generatedId;
        while (SessionStore.findSessionById(generatedId=generateId())!=null) {
            generatedId = generateId();
        }
        return generatedId;
    }

    private String generateId() {
        return String.valueOf(UUID.randomUUID());
    }
}
