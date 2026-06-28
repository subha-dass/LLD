package org.example;

import java.time.Duration;
import java.time.Instant;

public class CacheEntry <K,V>{
    private final K key;
    private volatile V value;
    private Instant createdAt;
    private Duration ttl;

    public CacheEntry(K key, V value, Duration ttl) {
        this.key = key;
        this.value = value;
        this.createdAt = Instant.now();
        this.ttl = ttl;
    }

    public K getKey() { return key; }

    public V getValue() { return value; }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean isExpired() {
        if (ttl == null) return false; // no TTL = never expires
        return Instant.now().isAfter(createdAt.plus(ttl));
    }

    public void resetTTL(Duration newTTL) {
        this.ttl = newTTL;
        this.createdAt = Instant.now();
    }
}
