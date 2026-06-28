package org.example;

public class ChangeEvent {
    private final String namespace;
    private final String key;
    private final ConfigEntry  oldValue;
    private final Object newValue;
    private final ChangeType changeType;
    private final long ceatedAt;

    public ChangeEvent(String namespace, String keu, ConfigEntry oldValue, Object newValue, ChangeType changeType) {
        this.namespace = namespace;
        this.key = keu;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeType = changeType;
        this.ceatedAt = System.currentTimeMillis();
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKeu() {
        return key;
    }

    public ConfigEntry getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public long getCeatedAt() {
        return ceatedAt;
    }
}
