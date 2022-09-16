package com.joojn.json.util;

public class EntrySet<K, V> {

    private final K key;
    private V value;

    public EntrySet(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}