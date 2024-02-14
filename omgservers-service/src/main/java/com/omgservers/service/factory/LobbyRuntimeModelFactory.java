package com.omgservers.service.factory;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyRuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public LobbyRuntimeModel create(final Long lobbyId,
                                    final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        return create(id, lobbyId, runtimeId);
    }

    public LobbyRuntimeModel create(final Long id,
                                    final Long lobbyId,
                                    final Long runtimeId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var lobbyRuntime = new LobbyRuntimeModel();
        lobbyRuntime.setId(id);
        lobbyRuntime.setLobbyId(lobbyId);
        lobbyRuntime.setCreated(now);
        lobbyRuntime.setModified(now);
        lobbyRuntime.setRuntimeId(runtimeId);
        lobbyRuntime.setDeleted(false);
        return lobbyRuntime;
    }
}
