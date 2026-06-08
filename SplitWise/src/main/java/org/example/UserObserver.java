package org.example;

public class UserObserver implements Observer{
    private User user;

    public UserObserver(User user) {
        this.user = user;
    }

    @Override
    public void update(String message) {
        System.out.println(
                user.getName() + " notified: " + message);
    }

}
