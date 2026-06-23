package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeliveryTracker {
    private final ConcurrentHashMap<String, DeliveryStatus> statuses
            = new ConcurrentHashMap<>();

    // CopyOnWriteArrayList — safe iteration during concurrent adds
    private final List<DeliveryListener> listeners
            = new CopyOnWriteArrayList<>();

    public void addListener(DeliveryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DeliveryListener listener) {
        listeners.remove(listener);
    }

    public void track(Notification notification) {
        statuses.put(notification.getId(), notification.getStatus());
    }

    public void update(Notification notification) {
        DeliveryStatus oldStatus = statuses.put(
                notification.getId(), notification.getStatus());
        DeliveryStatus newStatus = notification.getStatus();

        if (oldStatus != newStatus) {
            for (DeliveryListener listener : listeners) {
                try {
                    listener.onStatusChange(
                            notification.getId(), oldStatus, newStatus);
                } catch (Exception e) {
                    System.err.println("Listener error: " + e.getMessage());
                }
            }
        }
    }

    public DeliveryStatus getStatus(String notificationId) {
        return statuses.getOrDefault(
                notificationId, DeliveryStatus.CREATED);
    }

    public Map<String, DeliveryStatus> getAllStatuses() {
        return Collections.unmodifiableMap(statuses);
    }
}
