package org.example;

import java.util.HashMap;
import java.util.Map;

public class LRUPolicy<K,V> implements EvictionPolicy<K, V> {
    private static class Node<K> {
        K key;
        Node<K> prev, next;
        Node(K key) { this.key = key; }
    }

    private final Node<K> head = new Node<>(null); // sentinel
    private final Node<K> tail = new Node<>(null); // sentinel
    private final Map<K, Node<K>> nodeMap = new HashMap<>();

    public LRUPolicy() {
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void recordInsertion(K key) {
        Node<K> node = new Node<>(key);
        nodeMap.put(key, node);
        addToHead(node);
    }

    @Override
    public void recordAccess(K key) {
        Node<K> node = nodeMap.get(key);
        if (node != null) {
            removeNode(node);
            addToHead(node);
        }
    }

    @Override
    public K evict() {
        if (tail.prev == head) return null; // empty
        Node<K> lru = tail.prev;
        removeNode(lru);
        nodeMap.remove(lru.key);
        return lru.key;
    }

    @Override
    public void remove(K key) {
        Node<K> node = nodeMap.remove(key);
        if (node != null) removeNode(node);
    }

    private void addToHead(Node<K> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
