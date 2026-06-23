package org.example;

public class SMSChannel implements ChannelStrategy{
    @Override
    public void send(Notification notification) throws DeliveryException {
        System.out.println("[SMS] Sending to user "
                + notification.getUserId()
                + ": " + notification.getContent());
        simulateNetworkCall();
    }private void simulateNetworkCall() throws DeliveryException {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DeliveryException("SMS interrupted", e);
        }
    }
}
