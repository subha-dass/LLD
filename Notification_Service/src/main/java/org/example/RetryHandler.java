package org.example;

import java.util.concurrent.*;

public class RetryHandler {
    private final int maxRetries;
    private final long baseDelayMs;
    private final PriorityBlockingQueue<Notification> queue;
    private final ScheduledExecutorService scheduler;

    public RetryHandler(int maxRetries, long baseDelayMs, PriorityBlockingQueue<Notification> queue) {
        this.maxRetries = maxRetries;
        this.baseDelayMs = baseDelayMs;
        this.queue = queue;
        this.scheduler =  Executors.newScheduledThreadPool(2);
    }

    public void handleFailure(Notification notification) {
        int attempt = notification.incrementAttempt();

        if (attempt > maxRetries) {
            notification.setStatus(DeliveryStatus.FAILED);
            System.err.println("[RETRY] Permanently failed: "
                    + notification.getId()
                    + " after " + maxRetries + " retries");
            return;
        }

        // Exponential backoff: 2^attempt * base + jitter
        long delay = (long) Math.pow(2, attempt) * baseDelayMs
                + ThreadLocalRandom.current().nextLong(0, baseDelayMs);

        notification.setStatus(DeliveryStatus.RETRY_SCHEDULED);
        System.out.println("[RETRY] Scheduling attempt " + attempt
                + " for " + notification.getId()
                + " in " + delay + "ms");

        scheduler.schedule(
                () -> {
                    notification.setStatus(DeliveryStatus.QUEUED);
                    queue.offer(notification);
                },
                delay,
                TimeUnit.MILLISECONDS
        );
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
