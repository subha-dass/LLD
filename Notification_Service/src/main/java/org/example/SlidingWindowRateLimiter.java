package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter{
    private final int maxRequests;
    private final long windowMillis;

    private final ConcurrentHashMap<String, UserWindow> windows
            = new ConcurrentHashMap<>();
    public SlidingWindowRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }
    @Override
    public boolean allowRequest(String userId) {
        UserWindow window = windows.computeIfAbsent(
                userId, k -> new UserWindow(maxRequests,windowMillis));
        return window.tryAcquire();
    }
}
