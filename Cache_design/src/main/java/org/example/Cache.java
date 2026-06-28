package org.example;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache <K,V>{
    private static volatile Cache<?, ?> instance;
    private final ConcurrentHashMap<K, CacheEntry<K, V>> map;
    private final EvictionPolicy<K, V> evictionPolicy;
    private final int maxCapacity;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ScheduledExecutorService cleaner;

    @SuppressWarnings("unchecked")
    private Cache(int maxCapacity, EvictionPolicy<K, V> policy) {
        this.maxCapacity = maxCapacity;
        this.map = new ConcurrentHashMap<>(maxCapacity);
        this.evictionPolicy = policy;
        this.cleaner = Executors.newSingleThreadScheduledExecutor(
                r -> { Thread t = new Thread(r, "cache-ttl-cleaner");
                    t.setDaemon(true); return t; });
        cleaner.scheduleAtFixedRate(this::evictExpired,
                5, 5, TimeUnit.SECONDS);
    }

    // Double-checked locking — thread-safe singleton
    @SuppressWarnings("unchecked")
    public static <K, V> Cache<K, V> getInstance(
            int capacity, EvictionPolicy<K, V> policy) {
        if (instance == null) {
            synchronized (Cache.class) {
                if (instance == null)
                    instance = new Cache<>(capacity, policy);
            }
        }
        return (Cache<K, V>) instance;
    }

    public V get(K key) {
        rwLock.readLock().lock();
        try {
            CacheEntry<K, V> entry = map.get(key);
            if (entry == null) return null;
            if (entry.isExpired()) {
                // Upgrade to write lock for removal
                rwLock.readLock().unlock();
                rwLock.writeLock().lock();
                try {
                    map.remove(key);
                    evictionPolicy.remove(key);
                    return null;
                } finally {
                    rwLock.readLock().lock();
                    rwLock.writeLock().unlock();
                }
            }
            evictionPolicy.recordAccess(key);
            return entry.getValue();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void put(K key, V value, Duration ttl) {
        rwLock.writeLock().lock();
        try {
            if (map.containsKey(key)) {
                map.get(key).setValue(value);
                map.get(key).resetTTL(ttl);
                evictionPolicy.recordAccess(key);
                return;
            }
            if (map.size() >= maxCapacity) {
                K evictKey = evictionPolicy.evict();
                if (evictKey != null) map.remove(evictKey);
            }
            CacheEntry<K, V> entry = new CacheEntry<>(key, value, ttl);
            map.put(key, entry);
            evictionPolicy.recordInsertion(key);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void remove(K key) {
        rwLock.writeLock().lock();
        try {
            map.remove(key);
            evictionPolicy.remove(key);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    private void evictExpired() {
        rwLock.writeLock().lock();
        try {
            Iterator<Map.Entry<K, CacheEntry<K, V>>> it
                    = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, CacheEntry<K, V>> e = it.next();
                if (e.getValue().isExpired()) {
                    it.remove();
                    evictionPolicy.remove(e.getKey());
                }
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int size() { return map.size(); }

}
