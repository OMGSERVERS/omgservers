package com.omgservers.service.server.cache.impl.service.inmemory.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class InMemoryCache {

    final ObjectMapper objectMapper;

    final Map<String, Instant> keyExpirations;
    final Map<String, String> cacheValues;

    InMemoryCache(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        keyExpirations = new HashMap<>();
        cacheValues = new HashMap<>();
    }

    public synchronized <T> T get(final String key, Class<T> clazz) {
        final var keyExpiration = keyExpirations.get(key);
        final var cacheValue = cacheValues.get(key);
        if (Objects.nonNull(keyExpiration) && Objects.nonNull(cacheValue)) {
            if (keyExpiration.isAfter(Instant.now())) {
                try {
                    return objectMapper.readValue(cacheValue, clazz);
                } catch (IOException e) {
                    remove(key);
                    log.error("Invalid cache key \"{}\" removed", key);
                }
            } else {
                remove(key);
            }
        }

        return null;
    }

    public synchronized Instant getInstant(final String key) {
        return get(key, Instant.class);
    }

    public synchronized void put(final String key,
                                 final Object value,
                                 final Instant expiration) {
        try {
            final var stringValue = objectMapper.writeValueAsString(value);
            keyExpirations.put(key, expiration);
            cacheValues.put(key, stringValue);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }

    public synchronized void remove(final String key) {
        keyExpirations.remove(key);
        cacheValues.remove(key);
    }
}
