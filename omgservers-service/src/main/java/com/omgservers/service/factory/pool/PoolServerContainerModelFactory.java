package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerConfigModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerContainerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolServerContainerModel create(final Long poolId,
                                           final Long serverId,
                                           final Long runtimeId,
                                           final PoolServerContainerConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, serverId, runtimeId, config, idempotencyKey);
    }

    public PoolServerContainerModel create(final Long poolId,
                                           final Long serverId,
                                           final Long runtimeId,
                                           final PoolServerContainerConfigModel config,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, serverId, runtimeId, config, idempotencyKey);
    }

    public PoolServerContainerModel create(final Long id,
                                           final Long poolId,
                                           final Long serverId,
                                           final Long runtimeId,
                                           final PoolServerContainerConfigModel config,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolRuntimeServerContainerRef = new PoolServerContainerModel();
        poolRuntimeServerContainerRef.setId(id);
        poolRuntimeServerContainerRef.setIdempotencyKey(idempotencyKey);
        poolRuntimeServerContainerRef.setPoolId(poolId);
        poolRuntimeServerContainerRef.setServerId(serverId);
        poolRuntimeServerContainerRef.setCreated(now);
        poolRuntimeServerContainerRef.setModified(now);
        poolRuntimeServerContainerRef.setRuntimeId(runtimeId);
        poolRuntimeServerContainerRef.setConfig(config);
        poolRuntimeServerContainerRef.setDeleted(Boolean.FALSE);

        return poolRuntimeServerContainerRef;
    }
}
