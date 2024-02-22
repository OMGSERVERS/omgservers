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

    public LobbyModel create(final Long tenantId,
                             final Long versionId) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId);
    }

    public LobbyModel create(final Long id,
                             final Long tenantId,
                             final Long versionId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        final var runtimeId = generateIdOperation.generateId();

        final var lobby = new LobbyModel();
        lobby.setId(id);
        lobby.setCreated(now);
        lobby.setModified(now);
        lobby.setTenantId(tenantId);
        lobby.setVersionId(versionId);
        lobby.setRuntimeId(runtimeId);
        lobby.setDeleted(Boolean.FALSE);
        return lobby;
    }
}
