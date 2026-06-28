package org.example;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SnapshotManager {
    private final int maxSnapshots;
    private final LinkedHashMap<String, ConfigSnapshot> snapshots;

    public SnapshotManager(int maxSnapshots) {
        this.maxSnapshots = maxSnapshots;
        this.snapshots = new LinkedHashMap<>();
    }

    public synchronized String createSnapshot(
            ConcurrentHashMap<String, Namespace> namespaces) {
        // Sort namespace names to prevent deadlock
        List<String> sorted = new ArrayList<>(
                namespaces.keySet());
        Collections.sort(sorted);

        Map<String, Map<String, ConfigEntry>> data
                = new HashMap<>();

        // Acquire ALL write locks in sorted order
        for (String ns : sorted) {
            // We read via getAllEntries which uses readLock
            Namespace namespace = namespaces.get(ns);
            if (namespace != null) {
                Map<String, ConfigEntry> copied
                        = new HashMap<>();
                namespace.getAllEntries().forEach(
                        (k, v) -> copied.put(k, v.copy()));
                data.put(ns, copied);
            }
        }

        String snapshotId = UUID.randomUUID().toString();
        ConfigSnapshot snapshot = new ConfigSnapshot(
                snapshotId, data, Instant.now());
        snapshots.put(snapshotId, snapshot);
        return snapshotId;
    }
    public synchronized void restore(
            String snapshotId,
            ConcurrentHashMap<String, Namespace> namespaces) {
        ConfigSnapshot snapshot = snapshots.get(snapshotId);
        if (snapshot == null)
            throw new IllegalArgumentException(
                    "Snapshot not found: " + snapshotId);

        // Restore each namespace atomically
        for (Map.Entry<String, Map<String, ConfigEntry>> e
                : snapshot.getData().entrySet()) {
            Namespace ns = namespaces.computeIfAbsent(
                    e.getKey(), Namespace::new);
            Map<String, ConfigEntry> copied = new HashMap<>();
            e.getValue().forEach(
                    (k, v) -> copied.put(k, v.copy()));
            ns.replaceAll(copied);
        }
    }

    public synchronized List<String> listSnapshots() {
        return new ArrayList<>(snapshots.keySet());
    }
}
