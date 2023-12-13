package com.omgservers.service.factory;

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
                                     final Long userId,
                                     final Long clientId) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, userId, clientId);
    }

    public RuntimeClientModel create(final Long id,
                                     final Long runtimeId,
                                     final Long userId,
                                     final Long clientId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeClient = new RuntimeClientModel();
        runtimeClient.setId(id);
        runtimeClient.setRuntimeId(runtimeId);
        runtimeClient.setCreated(now);
        runtimeClient.setModified(now);
        runtimeClient.setUserId(userId);
        runtimeClient.setClientId(clientId);
        runtimeClient.setDeleted(false);
        return runtimeClient;
    }
}
