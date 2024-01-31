package com.omgservers.service.factory;

import com.omgservers.model.client.ClientModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientModel create(final Long userId,
                              final Long playerId,
                              final Long tenantId,
                              final Long versionId,
                              final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, playerId, tenantId, versionId, matchmakerId);
    }

    public ClientModel create(final Long id,
                              final Long userId,
                              final Long playerId,
                              final Long tenantId,
                              final Long versionId,
                              final Long matchmakerId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var client = new ClientModel();
        client.setId(id);
        client.setCreated(now);
        client.setModified(now);
        client.setUserId(userId);
        client.setPlayerId(playerId);
        client.setTenantId(tenantId);
        client.setVersionId(versionId);
        client.setMatchmakerId(matchmakerId);
        client.setDeleted(false);

        return client;
    }
}
