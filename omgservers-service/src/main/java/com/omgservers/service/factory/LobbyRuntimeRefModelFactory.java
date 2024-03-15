package com.omgservers.service.factory;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyRuntimeRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public LobbyRuntimeRefModel create(final Long lobbyId,
                                       final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, lobbyId, runtimeId, idempotencyKey);
    }

    public LobbyRuntimeRefModel create(final Long lobbyId,
                                       final Long runtimeId,
                                       final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, lobbyId, runtimeId, idempotencyKey);
    }

    public LobbyRuntimeRefModel create(final Long id,
                                       final Long lobbyId,
                                       final Long runtimeId,
                                       final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var lobbyRuntimeRef = new LobbyRuntimeRefModel();
        lobbyRuntimeRef.setId(id);
        lobbyRuntimeRef.setIdempotencyKey(idempotencyKey);
        lobbyRuntimeRef.setLobbyId(lobbyId);
        lobbyRuntimeRef.setCreated(now);
        lobbyRuntimeRef.setModified(now);
        lobbyRuntimeRef.setRuntimeId(runtimeId);
        lobbyRuntimeRef.setDeleted(Boolean.FALSE);
        return lobbyRuntimeRef;
    }
}
