package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionLobbyRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionLobbyRefModel create(final Long tenantId,
                                       final Long versionId,
                                       final Long lobbyId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, lobbyId, idempotencyKey);
    }

    public VersionLobbyRefModel create(final Long tenantId,
                                       final Long versionId,
                                       final Long lobbyId,
                                       final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, lobbyId, idempotencyKey);
    }

    public VersionLobbyRefModel create(final Long id,
                                       final Long tenantId,
                                       final Long versionId,
                                       final Long lobbyId,
                                       final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionLobbyRef = new VersionLobbyRefModel();
        versionLobbyRef.setId(id);
        versionLobbyRef.setTenantId(tenantId);
        versionLobbyRef.setVersionId(versionId);
        versionLobbyRef.setCreated(now);
        versionLobbyRef.setModified(now);
        versionLobbyRef.setIdempotencyKey(idempotencyKey);
        versionLobbyRef.setLobbyId(lobbyId);
        versionLobbyRef.setDeleted(false);
        return versionLobbyRef;
    }
}
