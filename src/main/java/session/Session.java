package session;

import java.util.Random;

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
        return new Random().ints('0', 'z'+1)
                .filter(i -> ('0'<=i && i<='9') || ('A'<=i && i<='Z') || ('a'<=i && i<='z'))
                .limit(ID_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
