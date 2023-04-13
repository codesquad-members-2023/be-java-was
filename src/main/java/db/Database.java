package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.*;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
       return findAll().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findAny();
    }

    public static List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public static void clear() {
        users.clear();
    }
}
