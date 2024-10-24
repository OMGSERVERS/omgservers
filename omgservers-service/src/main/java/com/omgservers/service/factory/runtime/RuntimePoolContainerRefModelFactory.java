package com.omgservers.service.factory.runtime;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePoolContainerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimePoolContainerRefModel create(final Long runtimeId,
                                               final Long poolId,
                                               final Long serverId,
                                               final Long containerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, poolId, serverId, containerId, idempotencyKey);
    }

    public RuntimePoolContainerRefModel create(final Long runtimeId,
                                               final Long poolId,
                                               final Long serverId,
                                               final Long containerId,
                                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, poolId, serverId, containerId, idempotencyKey);
    }

    public RuntimePoolContainerRefModel create(final Long id,
                                               final Long runtimeId,
                                               final Long poolId,
                                               final Long serverId,
                                               final Long containerId,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimePoolContainerRefModel = new RuntimePoolContainerRefModel();
        runtimePoolContainerRefModel.setId(id);
        runtimePoolContainerRefModel.setIdempotencyKey(idempotencyKey);
        runtimePoolContainerRefModel.setRuntimeId(runtimeId);
        runtimePoolContainerRefModel.setCreated(now);
        runtimePoolContainerRefModel.setModified(now);
        runtimePoolContainerRefModel.setPoolId(poolId);
        runtimePoolContainerRefModel.setServerId(serverId);
        runtimePoolContainerRefModel.setContainerId(containerId);
        runtimePoolContainerRefModel.setDeleted(false);
        return runtimePoolContainerRefModel;
    }
}
