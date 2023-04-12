package model;

import annotation.Param;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(@Param(name = "userId") String userId,
                @Param(name = "password") String password,
                @Param(name = "name") String name,
                @Param(name = "email") String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isLogined(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
