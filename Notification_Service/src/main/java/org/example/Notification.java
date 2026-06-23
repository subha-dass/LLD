package org.example;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Notification {
    private final String id;
    private final String userId;
    private final String content;
    private final ChannelType channelType;
    private final Priority priority;
    private final Instant createdAt;

    private volatile DeliveryStatus status;

    private final AtomicInteger attemptCount = new AtomicInteger(0);

    public Notification(String userId, String content, ChannelType channelType, Priority priority) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.content = content;
        this.channelType = channelType;
        this.priority = priority;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public Priority getPriority() {
        return priority;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Integer getAttemptCount() {
        return attemptCount.get();
    }
    public int incrementAttempt(){
        return attemptCount.incrementAndGet();
    }
}
