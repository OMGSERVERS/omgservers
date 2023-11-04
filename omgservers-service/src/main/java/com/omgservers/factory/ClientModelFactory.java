package com.omgservers.factory;

import com.omgservers.model.client.ClientModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientModel create(final Long userId,
                              final Long playerId,
                              final URI server,
                              final Long connectionId,
                              final Long versionId,
                              final Long defaultMatchmakerId,
                              final Long defaultRuntimeId) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, playerId, server, connectionId, versionId, defaultMatchmakerId, defaultRuntimeId);
    }

    public ClientModel create(final Long id,
                              final Long userId,
                              final Long playerId,
                              final URI server,
                              final Long connectionId,
                              final Long versionId,
                              final Long defaultMatchmakerId,
                              final Long defaultRuntimeId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ClientModel client = new ClientModel();
        client.setId(id);
        client.setUserId(userId);
        client.setPlayerId(playerId);
        client.setCreated(now);
        client.setModified(now);
        client.setServer(server);
        client.setConnectionId(connectionId);
        client.setVersionId(versionId);
        client.setDefaultMatchmakerId(defaultMatchmakerId);
        client.setDefaultRuntimeId(defaultRuntimeId);

        return client;
    }
}
