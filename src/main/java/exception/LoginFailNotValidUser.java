package exception;

public class LoginFailNotValidUser extends RuntimeException {
    public LoginFailNotValidUser(String message) {
        super(message);
    }
}
