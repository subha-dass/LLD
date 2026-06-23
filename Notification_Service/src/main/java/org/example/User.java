package org.example;

public class User {

    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final String deviceToken;

    public User(String id, String name, String email, String phone, String deviceToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.deviceToken = deviceToken;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeviceToken() {
        return deviceToken;
    }
}
