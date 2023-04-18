package exception;

public class ExceptionMessageBox {
    private String message;

    private ExceptionMessageBox(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionMessageBox from(String message) {
        return new ExceptionMessageBox(message);
    }
}
