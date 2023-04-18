package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
