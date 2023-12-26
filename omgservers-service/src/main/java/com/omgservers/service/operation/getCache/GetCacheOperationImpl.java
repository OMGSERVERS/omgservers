package com.omgservers.service.operation.getCache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideConflictException;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheManager;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCacheOperationImpl implements GetCacheOperation {

    final CacheManager cacheManager;
    final ObjectMapper objectMapper;

    @Override
    public <V> Uni<V> useWriteBasedCache(String keyPostfix, Function<String, Uni<V>> valueLoader, Class<V> clazz) {
        return useCache(getWriteBasedCache(), keyPostfix, valueLoader, clazz);
    }

    @Override
    public <V> Uni<V> useAccessBasedCache(String keyPostfix, Function<String, Uni<V>> valueLoader, Class<V> clazz) {
        return useCache(getAccessBasedCache(), keyPostfix, valueLoader, clazz);
    }

    <V> Uni<V> useCache(Cache cache, String keyPostfix, Function<String, Uni<V>> valueLoader, Class<V> clazz) {
        final var key = "omgservers:" + clazz.getSimpleName().toLowerCase() + ":" + keyPostfix;
        return cache.getAsync(key, k -> valueLoader.apply(key)
                        .map(originValue -> {
                            try {
                                log.info("Cache object, key={}, objectIdentity={}", key, System.identityHashCode(originValue));
                                return objectMapper.writeValueAsString(originValue);
                            } catch (IOException e) {
                                throw new ServerSideConflictException(e.getMessage(), e);
                            }
                        })
                )
                .map(cachedValue -> {
                    try {
                        return objectMapper.readValue(cachedValue, clazz);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(e.getMessage(), e);
                    }
                });
    }

    Cache getWriteBasedCache() {
        final var cacheName = CacheConfiguration.WRITE_BASED_CACHE;

        final var cache = cacheManager.getCache(cacheName)
                .orElseThrow(() -> new ServerSideConflictException(
                        cacheName + " is not present"));
        return cache;
    }

    Cache getAccessBasedCache() {
        final var cacheName = CacheConfiguration.ACCESS_BASED_CACHE;

        final var cache = cacheManager.getCache(cacheName)
                .orElseThrow(() -> new ServerSideConflictException(cacheName + " is not present"));
        return cache;
    }
}
