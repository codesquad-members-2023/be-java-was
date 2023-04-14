package exception;

public class UserInfoException extends RuntimeException {
    public UserInfoException() {
    }

    public UserInfoException(String message) {
        super(message);
    }
}
