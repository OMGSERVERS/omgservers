package com.omgservers.service.factory.client;

import com.omgservers.schema.model.client.ClientConfigDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
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
                              final Long deploymentId,
                              final ClientConfigDto clientConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, userId, playerId, deploymentId, clientConfig, idempotencyKey);
    }

    public ClientModel create(final Long userId,
                              final Long playerId,
                              final Long deploymentId,
                              final ClientConfigDto clientConfig,
                              final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, userId, playerId, deploymentId, clientConfig, idempotencyKey);
    }

    public ClientModel create(final Long id,
                              final Long userId,
                              final Long playerId,
                              final Long deploymentId,
                              final ClientConfigDto clientConfig,
                              final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var client = new ClientModel();
        client.setId(id);
        client.setIdempotencyKey(idempotencyKey);
        client.setCreated(now);
        client.setModified(now);
        client.setUserId(userId);
        client.setPlayerId(playerId);
        client.setDeploymentId(deploymentId);
        client.setConfig(clientConfig);
        client.setDeleted(false);

        return client;
    }
}
