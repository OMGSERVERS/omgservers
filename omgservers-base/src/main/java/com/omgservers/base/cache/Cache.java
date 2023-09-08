package com.omgservers.base.cache;

public interface Cache<K, V> {
    V getValue(K key);

    boolean cacheIfAbsent(K key, V value);

    V putValue(K key, V value);

    boolean hasCacheFor(K key);

    int sizeOfCache();
}
