package org.example;

import java.util.List;

public class Group {
    private String groupId;
    private String name;
    private List<User> users;

    public Group(String groupId, String name, List<User> users) {
        this.groupId = groupId;
        this.name = name;
        this.users = users;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
