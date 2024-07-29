package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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

    public PoolModel create() {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, idempotencyKey);
    }

    public PoolModel create(final Long id) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey);
    }

    public PoolModel create(final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, idempotencyKey);
    }

    public PoolModel create(final Long id,
                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var pool = new PoolModel();
        pool.setId(id);
        pool.setIdempotencyKey(idempotencyKey);
        pool.setCreated(now);
        pool.setModified(now);
        pool.setDeleted(Boolean.FALSE);
        return pool;
    }
}
