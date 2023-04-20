package model;

import annotation.Param;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static AtomicLong userIndexGenerator = new AtomicLong(0);
    private long userIndex;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(@Param(name = "userId") String userId,
                @Param(name = "password") String password,
                @Param(name = "name") String name,
                @Param(name = "email") String email) {
        this.userIndex = userIndexGenerator.incrementAndGet();
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getUserIndex() {
        return userIndex;
    }

    public boolean isLogined(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
