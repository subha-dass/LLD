package org.example;

public class LFUPolicy <K,V> implements EvictionPolicy<K,V>{
    @Override
    public void recordInsertion(K key) {

    }

    @Override
    public void recordAccess(K key) {

    }

    @Override
    public K evict() {
        return null;
    }

    @Override
    public void remove(K key) {

    }
}
