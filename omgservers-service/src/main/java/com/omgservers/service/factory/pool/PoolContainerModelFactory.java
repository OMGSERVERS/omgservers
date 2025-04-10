package com.omgservers.service.factory.pool;

import com.omgservers.schema.model.poolContainer.PoolContainerConfigDto;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolContainerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolContainerModel create(final Long poolId,
                                     final Long serverId,
                                     final Long runtimeId,
                                     final RuntimeQualifierEnum runtimeQualifier,
                                     final PoolContainerConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, serverId, runtimeId, runtimeQualifier, config, idempotencyKey);
    }

    public PoolContainerModel create(final Long poolId,
                                     final Long serverId,
                                     final Long runtimeId,
                                     final RuntimeQualifierEnum runtimeQualifier,
                                     final PoolContainerConfigDto config,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, serverId, runtimeId, runtimeQualifier, config, idempotencyKey);
    }

    public PoolContainerModel create(final Long id,
                                     final Long poolId,
                                     final Long serverId,
                                     final Long runtimeId,
                                     final RuntimeQualifierEnum runtimeQualifier,
                                     final PoolContainerConfigDto config,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolContainerModel = new PoolContainerModel();
        poolContainerModel.setId(id);
        poolContainerModel.setIdempotencyKey(idempotencyKey);
        poolContainerModel.setPoolId(poolId);
        poolContainerModel.setServerId(serverId);
        poolContainerModel.setCreated(now);
        poolContainerModel.setModified(now);
        poolContainerModel.setRuntimeId(runtimeId);
        poolContainerModel.setRuntimeQualifier(runtimeQualifier);
        poolContainerModel.setConfig(config);
        poolContainerModel.setDeleted(Boolean.FALSE);

        return poolContainerModel;
    }
}
