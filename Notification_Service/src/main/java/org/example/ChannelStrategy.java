package org.example;

public interface ChannelStrategy {
    void send(Notification notification) throws DeliveryException;
}
