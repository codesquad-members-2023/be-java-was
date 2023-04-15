package webserver.protocol.response;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class Cookie {
    private final String name;
    private final String value;
    private String domain;
    private String path;
    private Integer maxAge;
    private LocalDateTime expires;
    private Boolean secure;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append("=").append(value);

            Field[] fields = getClass().getDeclaredFields();

            for (Field field : fields) {
                Object value = field.get(this);
                if (!isIgnore(field.getName()) && value != null) {
                    sb.append("; ").append(toNameFormat(field.getName())).append("=").append(value);
                }
            }
            return sb.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isIgnore(String name) {
        return "name".equals(name) || "value".equals(name);
    }

    private String toNameFormat(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
