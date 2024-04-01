package com.omgservers.service.factory.pool;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRuntimeAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public PoolRuntimeAssignmentModel create(final Long poolId,
                                             final Long runtimeId,
                                             final Long serverId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, poolId, runtimeId, serverId, idempotencyKey);
    }

    public PoolRuntimeAssignmentModel create(final Long poolId,
                                             final Long runtimeId,
                                             final Long serverId,
                                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, runtimeId, serverId, idempotencyKey);
    }

    public PoolRuntimeAssignmentModel create(final Long id,
                                             final Long poolId,
                                             final Long runtimeId,
                                             final Long serverId,
                                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var poolRuntimeAssignment = new PoolRuntimeAssignmentModel();
        poolRuntimeAssignment.setId(id);
        poolRuntimeAssignment.setIdempotencyKey(idempotencyKey);
        poolRuntimeAssignment.setPoolId(poolId);
        poolRuntimeAssignment.setCreated(now);
        poolRuntimeAssignment.setModified(now);
        poolRuntimeAssignment.setRuntimeId(runtimeId);
        poolRuntimeAssignment.setServerId(serverId);
        poolRuntimeAssignment.setDeleted(false);

        return poolRuntimeAssignment;
    }
}
