package com.omgservers.service.factory.runtime;

import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePoolServerContainerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimePoolServerContainerRefModel create(final Long runtimeId,
                                                     final Long poolId,
                                                     final Long serverId,
                                                     final Long containerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, poolId, serverId, containerId, idempotencyKey);
    }

    public RuntimePoolServerContainerRefModel create(final Long runtimeId,
                                                     final Long poolId,
                                                     final Long serverId,
                                                     final Long containerId,
                                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, poolId, serverId, containerId, idempotencyKey);
    }

    public RuntimePoolServerContainerRefModel create(final Long id,
                                                     final Long runtimeId,
                                                     final Long poolId,
                                                     final Long serverId,
                                                     final Long containerId,
                                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimePoolServerContainerRef = new RuntimePoolServerContainerRefModel();
        runtimePoolServerContainerRef.setId(id);
        runtimePoolServerContainerRef.setIdempotencyKey(idempotencyKey);
        runtimePoolServerContainerRef.setRuntimeId(runtimeId);
        runtimePoolServerContainerRef.setCreated(now);
        runtimePoolServerContainerRef.setModified(now);
        runtimePoolServerContainerRef.setPoolId(poolId);
        runtimePoolServerContainerRef.setServerId(serverId);
        runtimePoolServerContainerRef.setContainerId(containerId);
        runtimePoolServerContainerRef.setDeleted(false);
        return runtimePoolServerContainerRef;
    }
}
