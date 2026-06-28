package org.example;

import com.sun.jdi.Value;

public class ConfigEntry {
    private final String key;
    private final Object value;
    private final long version;
    private final long expiryTime;
    private final long createdAt;

    public ConfigEntry(String key, Object value, long version, long expiryTime) {
        this.key = key;
        this.value = value;
        this.version = version;
        this.expiryTime = expiryTime;
        this.createdAt = System.currentTimeMillis();
    }

    public boolean isExpired(){
        return expiryTime<System.
        currentTimeMillis();
    }

    public <T> T getAs(Class<T> type){
        if(value==null)return null;
        if(!type.isInstance(value)){
            throw new ClassCastException("Casting problem during conf load" +
             key+" "+value);
        }return (T)value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public long getVersion() {
        return version;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public ConfigEntry copy() {
        return new ConfigEntry(
                key, value, version, expiryTime);
    }

    @Override
    public String toString() {
        return "ConfigEntry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", version=" + version +
                ", expiryTime=" + expiryTime +
                ", createdAt=" + createdAt +
                '}';
    }
}
