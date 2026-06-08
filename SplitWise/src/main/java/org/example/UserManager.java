package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final UserManager INSTANCE = new UserManager();

    private Map<String, User> users = new HashMap<>();

    private UserManager() {}

    public static UserManager getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        System.out.println("User added ::" + user.getName());
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
