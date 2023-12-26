package com.omgservers.service.operation.getCache;

import io.smallrye.mutiny.Uni;

import java.util.function.Function;

public interface GetCacheOperation {
    <V> Uni<V> useWriteBasedCache(String keyPostfix, Function<String, Uni<V>> valueLoader, Class<V> clazz);

    <V> Uni<V> useAccessBasedCache(String keyPostfix, Function<String, Uni<V>> valueLoader, Class<V> clazz);
}
