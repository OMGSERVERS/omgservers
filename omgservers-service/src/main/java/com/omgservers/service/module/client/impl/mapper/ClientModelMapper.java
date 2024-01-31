package com.omgservers.service.module.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.client.ClientModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientModelMapper {

    final ObjectMapper objectMapper;

    public ClientModel fromRow(Row row) {
        final var client = new ClientModel();
        client.setId(row.getLong("id"));
        client.setCreated(row.getOffsetDateTime("created").toInstant());
        client.setModified(row.getOffsetDateTime("modified").toInstant());
        client.setUserId(row.getLong("user_id"));
        client.setPlayerId(row.getLong("player_id"));
        client.setTenantId(row.getLong("tenant_id"));
        client.setVersionId(row.getLong("version_id"));
        client.setMatchmakerId(row.getLong("matchmaker_id"));
        client.setDeleted(row.getBoolean("deleted"));
        return client;
    }
}
