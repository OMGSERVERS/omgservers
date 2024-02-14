package com.omgservers.service.factory;

import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyModelFactory {

    final GenerateIdOperation generateIdOperation;

    public LobbyModel create() {
        final var id = generateIdOperation.generateId();
        return create(id);
    }

    public LobbyModel create(final Long id) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var lobby = new LobbyModel();
        lobby.setId(id);
        lobby.setCreated(now);
        lobby.setModified(now);
        lobby.setDeleted(Boolean.FALSE);
        return lobby;
    }
}
