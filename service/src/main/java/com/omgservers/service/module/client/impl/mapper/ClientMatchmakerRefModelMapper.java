package com.omgservers.service.module.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMatchmakerRefModelMapper {

    final ObjectMapper objectMapper;

    public ClientMatchmakerRefModel fromRow(final Row row) {
        final var clientMatchmakerRef = new ClientMatchmakerRefModel();
        clientMatchmakerRef.setId(row.getLong("id"));
        clientMatchmakerRef.setIdempotencyKey(row.getString("idempotency_key"));
        clientMatchmakerRef.setClientId(row.getLong("client_id"));
        clientMatchmakerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        clientMatchmakerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        clientMatchmakerRef.setMatchmakerId(row.getLong("matchmaker_id"));
        clientMatchmakerRef.setDeleted(row.getBoolean("deleted"));
        return clientMatchmakerRef;
    }
}
