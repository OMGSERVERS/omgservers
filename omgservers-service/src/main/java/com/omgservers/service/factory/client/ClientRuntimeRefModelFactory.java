package com.omgservers.service.factory.client;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientRuntimeRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientRuntimeRefModel create(final Long clientId,
                                        final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, clientId, runtimeId, idempotencyKey);
    }

    public ClientRuntimeRefModel create(final Long clientId,
                                        final Long runtimeId,
                                        final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, clientId, runtimeId, idempotencyKey);
    }

    public ClientRuntimeRefModel create(final Long id,
                                        final Long clientId,
                                        final Long runtimeId,
                                        final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var clientRuntimeRef = new ClientRuntimeRefModel();
        clientRuntimeRef.setId(id);
        clientRuntimeRef.setIdempotencyKey(idempotencyKey);
        clientRuntimeRef.setClientId(clientId);
        clientRuntimeRef.setCreated(now);
        clientRuntimeRef.setModified(now);
        clientRuntimeRef.setRuntimeId(runtimeId);
        clientRuntimeRef.setDeleted(false);

        return clientRuntimeRef;
    }
}
