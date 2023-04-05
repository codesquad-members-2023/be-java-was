package webserver.protocol;

public enum MethodType {
    GET;

    public boolean equals(String methodname) {
        return name().equals(methodname);
    }
}
