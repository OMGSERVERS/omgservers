package com.omgservers.service.module.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.client.ClientModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelMapper {

    final ObjectMapper objectMapper;

    public ClientModel fromRow(Row row) {
        final var client = new ClientModel();
        client.setId(row.getLong("id"));
        client.setUserId(row.getLong("user_id"));
        client.setPlayerId(row.getLong("player_id"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setModified(row.getOffsetDateTime("modified").toInstant());
        client.setServer(URI.create(row.getString("server")));
        client.setConnectionId(row.getLong("connection_id"));
        client.setVersionId(row.getLong("version_id"));
        client.setDefaultMatchmakerId(row.getLong("default_matchmaker_id"));
        client.setDefaultRuntimeId(row.getLong("default_runtime_id"));
        return client;
    }
}
