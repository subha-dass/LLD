package org.example;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigSnapshot {
    private final String id;
    private final Map<String, Map<String, ConfigEntry>> data;

    private final Instant createdAt;
    public ConfigSnapshot(
            String id,
            Map<String, Map<String, ConfigEntry>> data,
            Instant createdAt) {
        this.id = id;
        // Deep immutable copy
        Map<String, Map<String, ConfigEntry>> copy
                = new HashMap<>();
        data.forEach((ns, entries) ->
                copy.put(ns, Collections.unmodifiableMap(
                        new HashMap<>(entries))));
        this.data = Collections.unmodifiableMap(copy);
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public Map<String, Map<String, ConfigEntry>> getData() {
        return data;
    }
    public Instant getCreatedAt() { return createdAt; }

}
