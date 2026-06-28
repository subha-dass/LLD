package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        ConfigService config = ConfigService.getInstance();

        // Register observer
        config.addListener(event ->
                System.out.printf("  [EVENT] %s %s.%s: %s->%s%n",
                        event.getChangeType(),
                        event.getNamespace(),
                        event.getKeu(),
                        event.getOldValue(),
                        event.getNewValue()));

        // --- Basic CRUD ---
        System.out.println("=== Basic CRUD ===");
        config.put("", "app.name", "MyApp", -1);
        config.put("db", "pool.size", 10, -1);
        config.put("db.primary", "pool.size", 20, -1);

        // Hierarchical get: db.primary overrides db
        int poolSize = config.get(
                "db.primary", "pool.size", 5);
        System.out.println("db.primary pool.size = "
                + poolSize); // 20

        // Fallback to parent namespace
        String appName = config.get(
                "db.primary", "app.name", "default");
        System.out.println("app.name (from global) = "
                + appName); // MyApp

        // --- TTL Expiry ---
        System.out.println("\n=== TTL Expiry ===");
        config.put("cache", "ttl.key", "expires-soon",
                2000); // 2s TTL
        System.out.println("Before expiry: "
                + config.get("cache", "ttl.key", "gone"));
        Thread.sleep(2500);
        System.out.println("After expiry:  "
                + config.get("cache", "ttl.key", "gone"));

        // --- Snapshot/Restore ---
        System.out.println("\n=== Snapshot ===");
        String snapId = config.createSnapshot();
        System.out.println("Snapshot created: " + snapId);

        config.put("db", "pool.size", 99, -1);
        System.out.println("After change: "
                + config.get("db", "pool.size", 0));

        config.restoreSnapshot(snapId);
        System.out.println("After restore: "
                + config.get("db", "pool.size", 0));

        // --- Concurrent Access ---
        System.out.println("\n=== Concurrent Access ===");
        ExecutorService pool
                = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);
        AtomicInteger errors = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            final int idx = i;
            pool.submit(() -> {
                try {
                    if (idx % 2 == 0) {
                        config.put("stress",
                                "key-" + (idx % 10),
                                idx, -1);
                    } else {
                       // System.out.println("idx::"+idx);
                        config.get("stress",
                                "key-" + (idx % 10), -1);
                    }
                } catch (Exception e) {
                    errors.incrementAndGet();
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();
        System.out.println(
                "100 concurrent ops completed. Errors: "
                        + errors.get());

        // Cleanup
        // config.shutdown();

    }
}