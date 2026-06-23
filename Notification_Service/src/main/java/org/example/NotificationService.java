package org.example;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class NotificationService {
    private final PriorityBlockingQueue<Notification> queue;
    private final Map<ChannelType, ChannelStrategy> channels;
    private final RateLimiter rateLimiter;
    private final RetryHandler retryHandler;
    private final DeliveryTracker tracker;
    private final ExecutorService workerPool;
    private volatile boolean running = true;
    private static volatile NotificationService instance;

    private NotificationService(int workerCount) {
        this.queue = new PriorityBlockingQueue<>(100,
                Comparator.comparingInt(n -> n.getPriority().ordinal()));
        this.channels = new ConcurrentHashMap<>();
        this.rateLimiter = new SlidingWindowRateLimiter(10, 60_000);
        this.retryHandler = new RetryHandler(3, 1000, queue);
        this.tracker = new DeliveryTracker();
        this.workerPool = Executors.newFixedThreadPool(workerCount);

        for (int i = 0; i < workerCount; i++) {
            workerPool.submit(this::workerLoop);
        }
    }

    // Double-checked locking — thread-safe singleton
    public static NotificationService getInstance(int workerCount) {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null)
                    instance = new NotificationService(workerCount);
            }
        }
        return instance;
    }

    public void registerChannel(ChannelType type, ChannelStrategy ch) {
        channels.put(type, ch);
    }

    public DeliveryTracker getTracker() {
        return tracker;
    }

    public String send(Notification notification) {
        if (!rateLimiter.allowRequest(notification.getUserId())) {
            throw new RateLimitExceededException(
                    "User " + notification.getUserId() + " rate limited");
        }
        notification.setStatus(DeliveryStatus.QUEUED);
        tracker.track(notification);
        queue.offer(notification);
        return notification.getId();
    }

    private void workerLoop() {
        while (running) {
            try {
                Notification n = queue.take(); // blocks if empty
                dispatch(n);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[WORKER] Unexpected: " + e.getMessage());
            }
        }
    }
    private void dispatch(Notification n) {
        ChannelStrategy channel = channels.get(n.getChannelType());
        if (channel == null) {
            n.setStatus(DeliveryStatus.FAILED);
            tracker.update(n);
            return;
        }
        try {
            n.setStatus(DeliveryStatus.SENDING);
            tracker.update(n);
            channel.send(n);
            n.setStatus(DeliveryStatus.DELIVERED);
            tracker.update(n);
        } catch (DeliveryException e) {
            retryHandler.handleFailure(n);
        }
    }

    public void shutdown() {
        running = false;
        workerPool.shutdownNow();
        retryHandler.shutdown();
    }
    }
