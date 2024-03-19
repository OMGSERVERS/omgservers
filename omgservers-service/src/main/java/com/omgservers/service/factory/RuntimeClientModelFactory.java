package com.omgservers.service.factory;

import com.omgservers.model.runtimeClient.RuntimeClientConfigModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeClientModel create(final Long runtimeId,
                                     final Long clientId) {
        final var id = generateIdOperation.generateId();
        final var config = RuntimeClientConfigModel.create();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeClientModel create(final Long runtimeId,
                                     final Long clientId,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var config = RuntimeClientConfigModel.create();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeClientModel create(final Long runtimeId,
                                     final Long clientId,
                                     final RuntimeClientConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeClientModel create(final Long runtimeId,
                                     final Long clientId,
                                     final RuntimeClientConfigModel config,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, clientId, config, idempotencyKey);
    }

    public RuntimeClientModel create(final Long id,
                                     final Long runtimeId,
                                     final Long clientId,
                                     final RuntimeClientConfigModel config,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeClient = new RuntimeClientModel();
        runtimeClient.setId(id);
        runtimeClient.setIdempotencyKey(idempotencyKey);
        runtimeClient.setRuntimeId(runtimeId);
        runtimeClient.setCreated(now);
        runtimeClient.setModified(now);
        runtimeClient.setClientId(clientId);
        runtimeClient.setLastActivity(now);
        runtimeClient.setConfig(config);
        runtimeClient.setDeleted(false);
        return runtimeClient;
    }
}
