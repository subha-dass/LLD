package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Namespace {
    private final String name;
    private final ConcurrentHashMap<String,ConfigEntry> entries=new ConcurrentHashMap<>();
    private final ReadWriteLock lock= new ReentrantReadWriteLock();

    private final AtomicLong versionCounter =new AtomicLong();

    public Namespace(String name) {
        this.name = name;
    }

    public ConfigEntry put(String key, Object value, long ttlMs) {
        lock.writeLock().lock();
        try {
            long version = versionCounter.incrementAndGet();
            ConfigEntry old = entries.get(key);
            long expiryTime = ttlMs > 0
                    ? System.currentTimeMillis() + ttlMs : -1;
            ConfigEntry entry = new ConfigEntry(
                    key, value, version, ttlMs);
            entries.put(key, entry);
            return old;
        } finally {
            lock.writeLock().unlock();
        }
    }
    public ConfigEntry get(String key) {
        lock.readLock().lock();
        try {
            ConfigEntry entry = entries.get(key);
            if (entry != null && entry.isExpired()) {
                // Lazy eviction — upgrade to write
                // won't block here; just return null
                return null;
            }
            return entry;
        } finally {
            lock.readLock().unlock();
        }
    } public ConfigEntry delete(String key) {
        lock.writeLock().lock();
        try {
            return entries.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    } public void evictExpired() {
        lock.writeLock().lock();
        try {
            entries.entrySet().removeIf(
                    e -> e.getValue().isExpired());
        } finally {
            lock.writeLock().unlock();
        }
    }
    public Map<String, ConfigEntry> getAllEntries() {
        lock.readLock().lock();
        try {
            return new HashMap<>(entries);
        } finally {
            lock.readLock().unlock();
        }
    }public void replaceAll(Map<String, ConfigEntry> snapshot) {
        lock.writeLock().lock();
        try {
            entries.clear();
            entries.putAll(snapshot);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public String getName() { return name; }
}
