package com.omgservers.base.cache;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCache<K, V> implements Cache<K, V> {

    final Map<K, V> cache;

    public InMemoryCache() {
        cache = new HashMap<>();
    }

    @Override
    public synchronized V getValue(K key) {
        return cache.get(key);
    }

    @Override
    public synchronized V cacheIfAbsent(K key, V value) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            cache.put(key, value);
            return value;
        }
    }

    @Override
    public synchronized V putValue(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public synchronized boolean hasCacheFor(K key) {
        return cache.containsKey(key);
    }

    @Override
    public synchronized int sizeOfCache() {
        return cache.size();
    }
}
