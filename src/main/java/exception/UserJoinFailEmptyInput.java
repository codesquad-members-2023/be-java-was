package exception;

public class UserJoinFailEmptyInput extends RuntimeException {
    public UserJoinFailEmptyInput(String message) {
        super(message);
    }
}
