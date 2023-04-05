package util;

public enum HttpStatusSeries {
    SUCCESS(2),
    REDIRECT(3),
    CLIENT_ERROR(4),
    SERVER_ERROR(5);

    private final int value;

    HttpStatusSeries(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
