package org.example;

import java.util.List;
import java.util.concurrent.*;

public class ConfigService {
    private static volatile ConfigService instance;
    private final ConcurrentHashMap<String, Namespace> namespaces
            = new ConcurrentHashMap<>();
    private final List<ChangeListener> listeners
            = new CopyOnWriteArrayList<>();
    private final ExecutorService notifierPool
            = Executors.newFixedThreadPool(4);
    private final SnapshotManager snapshotManager
            = new SnapshotManager(10);
    private final ScheduledExecutorService reaper
            = Executors.newSingleThreadScheduledExecutor();

    private ConfigService() {
        reaper.scheduleAtFixedRate(
                this::evictExpired, 1, 1, TimeUnit.SECONDS);
    }

    // Double-checked locking — thread-safe singleton
    public static ConfigService getInstance() {
        if (instance == null) {
            synchronized (ConfigService.class) {
                if (instance == null)
                    instance = new ConfigService();
            }
        }
        return instance;
    }
    public String createSnapshot() {
        return snapshotManager.createSnapshot(namespaces);
    }

    public void put(String ns, String key, Object value, long ttlMs) {
        Namespace namespace = namespaces.computeIfAbsent(
                ns, Namespace::new);
        ConfigEntry old = namespace.put(key, value, ttlMs);
        ChangeEvent event = new ChangeEvent(
                ns, key, old,value, ChangeType.UPDATED);
        notifyListeners(event);
    }
    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(ChangeEvent event) {
        for (ChangeListener l : listeners) {
            notifierPool.submit(() -> {
                try { l.onChange(event); }
                catch (Exception e) {
                    // log and continue — bad listener
                    // must not break the service
                }
            });
        }
    }

    private void evictExpired() {
        for (Namespace ns : namespaces.values()) {
            ns.evictExpired();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String ns, String key, T defaultVal) {
        // Walk up namespace hierarchy
        String current = ns;
        while (current != null && !current.isEmpty()) {
            Namespace namespace = namespaces.get(current);
            if (namespace != null) {
                ConfigEntry entry = namespace.get(key);
                if (entry != null && !entry.isExpired())
                    return (T) entry.getValue();
            }
            int dot = current.lastIndexOf('.');
            current = dot > 0 ? current.substring(0, dot) : "";
        }
        // Check global namespace
        Namespace global = namespaces.get("");
        if (global != null) {
            ConfigEntry entry = global.get(key);
            if (entry != null && !entry.isExpired())
                return (T) entry.getValue();
        }
        return defaultVal;
    }

    public boolean delete(String ns, String key) {
        Namespace namespace = namespaces.get(ns);
        if (namespace == null) return false;
        ConfigEntry removed = namespace.delete(key);
        if (removed != null) {
            notifyListeners(new ChangeEvent(
                    ns, key, (ConfigEntry) removed.getValue(), null,
                    ChangeType.DELETED));
            return true;
        }
        return false;
    }
    public void restoreSnapshot(String snapshotId) {
        snapshotManager.restore(snapshotId, namespaces);
        notifyListeners(new ChangeEvent(
                "*", "*", null, null, ChangeType.RESTORED));
    }

}
