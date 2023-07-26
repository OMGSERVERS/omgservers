package com.omgservers.application.module.userModule.model.client;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientModel create(final Long playerId,
                              final URI server,
                              final Long connectionId) {
        final var id = generateIdOperation.generateId();
        return create(id, playerId, server, connectionId);
    }

    public ClientModel create(final Long id,
                              final Long playerId,
                              final URI server,
                              final Long connectionId) {
        Instant now = Instant.now();

        ClientModel client = new ClientModel();
        client.setId(id);
        client.setPlayerId(playerId);
        client.setCreated(now);
        client.setServer(server);
        client.setConnectionId(connectionId);

        return client;
    }
}
