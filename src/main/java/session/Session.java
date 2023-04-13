package session;

import java.util.Random;

public class Session {
    private final int ID_SIZE = 32;

    private String id;
    private Object value;

    public String getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    public Session(Object value) {
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
        return new Random().ints('0', 'z'+1)
                .filter(i -> ('0'<=i && i<='9') || ('A'<=i && i<='Z') || ('a'<=i && i<='z'))
                .limit(ID_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
