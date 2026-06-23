package org.example;

public interface RateLimiter {
    boolean allowRequest(String userId);
}
