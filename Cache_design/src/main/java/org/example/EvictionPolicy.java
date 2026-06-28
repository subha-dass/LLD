package org.example;

    public interface EvictionPolicy<K, V> {

        /** Called when a brand-new key is inserted into the cache. */
        void recordInsertion(K key);

        /** Called on every get() or put() for an existing key. */
        void recordAccess(K key);

        /**
         * Returns the key that should be evicted.
         * Returns null if nothing to evict.
         */
        K evict();

        /** Called when a key is explicitly removed or expired. */
        void remove(K key);
    }

