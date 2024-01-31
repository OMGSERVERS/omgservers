package com.omgservers.service.factory;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientRuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientRuntimeModel create(final Long clientId,
                                     final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        return create(id, clientId, runtimeId);
    }

    public ClientRuntimeModel create(final Long id,
                                     final Long clientId,
                                     final Long runtimeId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var clientMatch = new ClientRuntimeModel();
        clientMatch.setId(id);
        clientMatch.setClientId(clientId);
        clientMatch.setCreated(now);
        clientMatch.setModified(now);
        clientMatch.setRuntimeId(runtimeId);
        clientMatch.setDeleted(false);

        return clientMatch;
    }
}
