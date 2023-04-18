package exception;

public class UserJoinDuplicateKey extends RuntimeException {
    public UserJoinDuplicateKey(String message) {
        super(message);
    }
}
