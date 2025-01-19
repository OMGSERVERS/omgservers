package com.omgservers.service.factory.client;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMatchmakerRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientMatchmakerRefModel create(final Long clientId,
                                           final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, clientId, matchmakerId, idempotencyKey);
    }

    public ClientMatchmakerRefModel create(final Long clientId,
                                           final Long matchmakerId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, clientId, matchmakerId, idempotencyKey);
    }

    public ClientMatchmakerRefModel create(final Long id,
                                           final Long clientId,
                                           final Long matchmakerId,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var clientMatchmakerRef = new ClientMatchmakerRefModel();
        clientMatchmakerRef.setId(id);
        clientMatchmakerRef.setIdempotencyKey(idempotencyKey);
        clientMatchmakerRef.setClientId(clientId);
        clientMatchmakerRef.setCreated(now);
        clientMatchmakerRef.setModified(now);
        clientMatchmakerRef.setMatchmakerId(matchmakerId);
        clientMatchmakerRef.setDeleted(false);

        return clientMatchmakerRef;
    }
}
