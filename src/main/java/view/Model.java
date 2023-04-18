package view;

import java.util.*;

public class Model {
    private final Map<String, Object> objects;

    private Model(Map<String, Object> objects) {
        this.objects = objects;
    }

    public void addObject(String key, Object obj) {
        objects.put(key, obj);
    }

    public Optional<Object> getObject(String key) {
        return Optional.ofNullable(objects.get(key));
    }

    public int size() {
        return objects.size();
    }

    public List<String> getKeys() {
        return new ArrayList<>(objects.keySet());
    }

    public static Model from(String key, Object item) {
        return new Model(Map.of(key, item));
    }

    public static Model create() {
        return new Model(new HashMap<>());
    }
}
