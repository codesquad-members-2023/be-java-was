package webserver.protocol;

public enum Method {
    GET;

    public boolean equals(String methodname) {
        return name().equals(methodname);
    }
}
