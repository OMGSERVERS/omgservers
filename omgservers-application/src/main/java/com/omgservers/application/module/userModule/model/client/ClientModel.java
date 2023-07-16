package com.omgservers.application.module.userModule.model.client;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    static public ClientModel create(final UUID player,
                                     final URI server,
                                     final UUID connection) {
        return create(player, UUID.randomUUID(), server, connection);
    }

    static public ClientModel create(final UUID player,
                                     final UUID uuid,
                                     final URI server,
                                     final UUID connection) {
        Instant now = Instant.now();

        ClientModel client = new ClientModel();
        client.setPlayer(player);
        client.setCreated(now);
        client.setUuid(uuid);
        client.setServer(server);
        client.setConnection(connection);

        return client;
    }

    static public void validateClient(ClientModel client) {
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }
    }

    UUID player;
    @ToString.Exclude
    Instant created;
    UUID uuid;
    URI server;
    UUID connection;
}
