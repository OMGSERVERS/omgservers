package com.omgservers.service.factory.runtime;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentConfigModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeAssignmentModel create(final Long runtimeId,
                                         final Long clientId) {
        final var id = generateIdOperation.generateId();
        final var config = RuntimeAssignmentConfigModel.create();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeAssignmentModel create(final Long runtimeId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var config = RuntimeAssignmentConfigModel.create();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeAssignmentModel create(final Long runtimeId,
                                         final Long clientId,
                                         final RuntimeAssignmentConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeAssignmentModel create(final Long runtimeId,
                                         final Long clientId,
                                         final RuntimeAssignmentConfigModel config,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeAssignmentModel create(final Long id,
                                         final Long runtimeId,
                                         final Long clientId,
                                         final RuntimeAssignmentConfigModel config,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeAssignment = new RuntimeAssignmentModel();
        runtimeAssignment.setId(id);
        runtimeAssignment.setIdempotencyKey(idempotencyKey);
        runtimeAssignment.setRuntimeId(runtimeId);
        runtimeAssignment.setCreated(now);
        runtimeAssignment.setModified(now);
        runtimeAssignment.setClientId(clientId);
        runtimeAssignment.setLastActivity(now);
        runtimeAssignment.setConfig(config);
        runtimeAssignment.setDeleted(false);
        return runtimeAssignment;
    }
}
