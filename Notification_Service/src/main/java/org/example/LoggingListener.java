package org.example;

public class LoggingListener implements DeliveryListener{
    @Override
    public void onStatusChange(String notificationId, DeliveryStatus oldStatus, DeliveryStatus newStatus) {
        System.out.println("[LOG] Notification " +notificationId
                + ": " + oldStatus + " -> " + newStatus);
    }
}
