package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionLobbyRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionLobbyRequestModel create(final Long tenantId,
                                           final Long versionId) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, lobbyId, idempotencyKey);
    }

    public VersionLobbyRequestModel create(final Long tenantId,
                                           final Long versionId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        return create(id, tenantId, versionId, lobbyId, idempotencyKey);
    }

    public VersionLobbyRequestModel create(final Long id,
                                           final Long tenantId,
                                           final Long versionId,
                                           final Long lobbyId,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionLobbyRequest = new VersionLobbyRequestModel();
        versionLobbyRequest.setId(id);
        versionLobbyRequest.setTenantId(tenantId);
        versionLobbyRequest.setVersionId(versionId);
        versionLobbyRequest.setCreated(now);
        versionLobbyRequest.setModified(now);
        versionLobbyRequest.setIdempotencyKey(idempotencyKey);
        versionLobbyRequest.setLobbyId(lobbyId);
        versionLobbyRequest.setDeleted(false);
        return versionLobbyRequest;
    }
}
