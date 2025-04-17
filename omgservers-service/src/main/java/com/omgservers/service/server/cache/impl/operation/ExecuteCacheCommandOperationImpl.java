package com.omgservers.service.server.cache.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.redis.datasource.value.SetArgs;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ApplicationScoped
class ExecuteCacheCommandOperationImpl implements ExecuteCacheCommandOperation {

    final ReactiveValueCommands<String, String> reactiveValueCommands;
    final ReactiveKeyCommands<String> reactiveKeyCommands;
    final ObjectMapper objectMapper;

    public ExecuteCacheCommandOperationImpl(final ReactiveRedisDataSource reactiveRedisDataSource,
                                            final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        reactiveValueCommands = reactiveRedisDataSource.value(String.class, String.class);
        reactiveKeyCommands = reactiveRedisDataSource.key(String.class);
    }

    @Override
    public <T> Uni<T> get(final String key, final Class<T> clazz) {
        return reactiveValueCommands.get(key)
                .map(value -> {
                    if (Objects.isNull(value)) {
                        return null;
                    }

                    try {
                        final var parsedValue = objectMapper.readValue(value, clazz);
                        return parsedValue;
                    } catch (IOException e) {
                        log.error("Invalid cache key \"{}\" found", key);
                        return null;
                    }
                });
    }

    @Override
    public <T> Uni<Map<String, T>> getKeys(final List<String> keys, final Class<T> clazz) {
        if (keys.isEmpty()) {
            return Uni.createFrom().item(new HashMap<>());
        } else {
            return reactiveValueCommands.mget(keys.toArray(new String[0]))
                    .map(values -> {
                        final var result = new HashMap<String, T>();

                        for (final String key : values.keySet()) {
                            final var value = values.get(key);

                            if (Objects.isNull(value)) {
                                continue;
                            }

                            try {
                                final var parsedValue = objectMapper.readValue(value, clazz);
                                result.put(key, parsedValue);
                            } catch (IOException e) {
                                log.error("Invalid cache key \"{}\" found", key);
                            }
                        }

                        return result;
                    });
        }
    }

    @Override
    public Uni<Instant> getInstant(final String key) {
        return get(key, Instant.class);
    }

    @Override
    public Uni<Map<String, Instant>> getInstantKeys(final List<String> keys) {
        return getKeys(keys, Instant.class);
    }

    @Override
    public Uni<Void> put(final String key,
                         final Object value,
                         final long timeout) {
        try {
            final var stringValue = objectMapper.writeValueAsString(value);
            if (timeout > 0) {
                final var setArgs = new SetArgs().ex(timeout);
                return reactiveValueCommands.set(key, stringValue, setArgs);
            } else {
                return reactiveValueCommands.set(key, stringValue);
            }
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
