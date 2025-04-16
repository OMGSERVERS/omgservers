package com.omgservers.service.server.cache.impl.operation;

import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ExecuteCacheCommandOperation {

    <T> Uni<T> get(final String key, Class<T> clazz);

    <T> Uni<Map<String, T>> getKeys(final List<String> keys, Class<T> clazz);

    Uni<Instant> getInstant(final String key);

    Uni<Map<String, Instant>> getInstantKeys(final List<String> keys);

    Uni<Void> put(String key, Object value, long timeout);
}
