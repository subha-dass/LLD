package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // 1. Create singleton service with 4 worker threads
        NotificationService service
                = NotificationService.getInstance(4);

        // 2. Register channels (Strategy pattern)
        service.registerChannel(ChannelType.EMAIL, new EmailChannel());
        service.registerChannel(ChannelType.SMS,   new SMSChannel());
        //service.registerChannel(ChannelType.PUSH,  new PushChannel());

        // 3. Add observer for delivery tracking
        DeliveryTracker tracker = service.getTracker();
        tracker.addListener(new LoggingListener());

        // 4. Simulate concurrent senders
        int senderCount = 10;
        CountDownLatch latch = new CountDownLatch(senderCount);
        ExecutorService senders = Executors.newFixedThreadPool(senderCount);

        for (int i = 0; i < senderCount; i++) {
            final int idx = i;
            senders.submit(() -> {
                try {
                    ChannelType ch = ChannelType.values()[idx % 3];
                    Priority pri = Priority.values()[idx % 4];
                    Notification n = new Notification(
                            "user-" + (idx % 3),
                            "Hello #" + idx,
                            ch, pri
                    );
                    String id = service.send(n);
                    System.out.println("Queued: " + id
                            + " [" + pri + ", " + ch + "]");
                } catch (RateLimitExceededException e) {
                    System.out.println("Rate limited: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
            senders.shutdown();

            // 5. Wait for dispatch to complete
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // 6. Print final statuses
        System.out.println("\n=== Delivery Report ===");
        tracker.getAllStatuses().forEach((id, status) ->
                System.out.println(id + " -> " + status));

        service.shutdown();
    }
}