package com.omgservers.service.factory.runtime;

import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeServerContainerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeServerContainerRefModel create(final Long runtimeId,
                                                 final Long serverId,
                                                 final Long serverContainerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, serverId, serverContainerId, idempotencyKey);
    }

    public RuntimeServerContainerRefModel create(final Long runtimeId,
                                                 final Long serverId,
                                                 final Long serverContainerId,
                                                 final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, serverId, serverContainerId, idempotencyKey);
    }

    public RuntimeServerContainerRefModel create(final Long id,
                                                 final Long runtimeId,
                                                 final Long serverId,
                                                 final Long serverContainerId,
                                                 final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeServerContainerRef = new RuntimeServerContainerRefModel();
        runtimeServerContainerRef.setId(id);
        runtimeServerContainerRef.setIdempotencyKey(idempotencyKey);
        runtimeServerContainerRef.setRuntimeId(runtimeId);
        runtimeServerContainerRef.setCreated(now);
        runtimeServerContainerRef.setModified(now);
        runtimeServerContainerRef.setServerId(serverId);
        runtimeServerContainerRef.setServerContainerId(serverContainerId);
        runtimeServerContainerRef.setDeleted(false);
        return runtimeServerContainerRef;
    }
}
