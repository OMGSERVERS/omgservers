package com.omgservers.service.factory.pool;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRuntimeServerContainerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolRuntimeServerContainerRefModel create(final Long poolId,
                                                     final Long runtimeId,
                                                     final Long serverId,
                                                     final Long containerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, runtimeId, serverId, containerId, idempotencyKey);
    }

    public PoolRuntimeServerContainerRefModel create(final Long poolId,
                                                     final Long runtimeId,
                                                     final Long serverId,
                                                     final Long containerId,
                                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, runtimeId, serverId, containerId, idempotencyKey);
    }

    public PoolRuntimeServerContainerRefModel create(final Long id,
                                                     final Long poolId,
                                                     final Long runtimeId,
                                                     final Long serverId,
                                                     final Long containerId,
                                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolRuntimeServerContainerRef = new PoolRuntimeServerContainerRefModel();
        poolRuntimeServerContainerRef.setId(id);
        poolRuntimeServerContainerRef.setIdempotencyKey(idempotencyKey);
        poolRuntimeServerContainerRef.setPoolId(poolId);
        poolRuntimeServerContainerRef.setCreated(now);
        poolRuntimeServerContainerRef.setModified(now);
        poolRuntimeServerContainerRef.setRuntimeId(runtimeId);
        poolRuntimeServerContainerRef.setServerId(serverId);
        poolRuntimeServerContainerRef.setContainerId(containerId);
        poolRuntimeServerContainerRef.setDeleted(false);

        return poolRuntimeServerContainerRef;
    }
}
