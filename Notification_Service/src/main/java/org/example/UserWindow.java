package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class UserWindow {
    private final int maxRequests;
    private final long windowMillis;

    public UserWindow(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    private final Deque<Long> timestamps = new ArrayDeque<>();
    private final ReentrantLock lock = new ReentrantLock();

    boolean tryAcquire() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            // Evict expired timestamps
            while (!timestamps.isEmpty()
                    && timestamps.peekFirst() <= now - windowMillis) {
                timestamps.pollFirst();
            }
            if (timestamps.size() >= maxRequests) {
                return false; // rate limited
            }
            timestamps.addLast(now);
            return true;
        } finally {
            lock.unlock();
        }
    }
}

