package com.omgservers.base.cache;

import java.util.Optional;

public interface Cache<K, V> {
    Optional<V> getValue(K key);

    V cacheIfAbsent(K key, V value);

    V putValue(K key, V value);

    boolean hasCacheFor(K key);

    int sizeOfCache();
}
