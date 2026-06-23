package org.example;

public interface DeliveryListener {
    void onStatusChange(String notificationId,
                        DeliveryStatus oldStatus,
                        DeliveryStatus newStatus);
}
