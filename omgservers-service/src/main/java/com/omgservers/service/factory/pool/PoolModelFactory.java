package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.pool.PoolConfigDto;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolModel create(final PoolConfigDto poolConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, poolConfig, idempotencyKey);
    }

    public PoolModel create(final Long id,
                            final PoolConfigDto poolConfig) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolConfig, idempotencyKey);
    }

    public PoolModel create(final PoolConfigDto poolConfig,
                            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolConfig, idempotencyKey);
    }

    public PoolModel create(final Long id,
                            final PoolConfigDto poolConfig,
                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var pool = new PoolModel();
        pool.setId(id);
        pool.setIdempotencyKey(idempotencyKey);
        pool.setCreated(now);
        pool.setModified(now);
        pool.setConfig(poolConfig);
        pool.setDeleted(Boolean.FALSE);
        return pool;
    }
}
