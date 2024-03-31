package com.omgservers.service.factory.pool;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolServerRefModel create(final Long poolId,
                                     final Long serverId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, serverId, idempotencyKey);
    }

    public PoolServerRefModel create(final Long poolId,
                                     final Long serverId,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, serverId, idempotencyKey);
    }

    public PoolServerRefModel create(final Long id,
                                     final Long poolId,
                                     final Long serverId,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolServerRef = new PoolServerRefModel();
        poolServerRef.setId(id);
        poolServerRef.setIdempotencyKey(idempotencyKey);
        poolServerRef.setPoolId(poolId);
        poolServerRef.setCreated(now);
        poolServerRef.setModified(now);
        poolServerRef.setServerId(serverId);
        poolServerRef.setDeleted(false);

        return poolServerRef;
    }
}
