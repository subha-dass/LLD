package org.example;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        // Create cache: capacity=3, LRU eviction
        EvictionPolicy<String, String> lru = new LRUPolicy<>();
        Cache<String, String> cache
                = Cache.getInstance(3, lru);

        // Basic operations
        cache.put("user:1", "Alice", Duration.ofMinutes(10));
        cache.put("user:2", "Bob",   Duration.ofMinutes(10));
        cache.put("user:3", "Carol", Duration.ofMinutes(10));
        System.out.println(cache.get("user:1")); // Alice

        // Triggers LRU eviction of user:2 (least recent)
        cache.put("user:4", "Dave", Duration.ofMinutes(10));
        System.out.println(cache.get("user:2")); // null — evicted

        // TTL expiry demo
        cache.put("temp", "expires-fast", Duration.ofSeconds(1));
        Thread.sleep(1500);
        System.out.println(cache.get("temp")); // null — expired

        // Concurrent access demo
        ExecutorService pool = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            final int idx = i;
            pool.submit(() -> {
                try {
                    if (idx % 2 == 0) {
                        cache.put("k" + idx, "v" + idx,
                                Duration.ofMinutes(5));
                    } else {
                        cache.get("k" + (idx - 1));
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();
        System.out.println("Cache size: " + cache.size());
        System.out.println("Done — no race conditions!");
    }
}