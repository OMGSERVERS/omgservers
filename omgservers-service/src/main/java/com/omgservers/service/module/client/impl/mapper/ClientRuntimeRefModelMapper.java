package com.omgservers.service.module.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientRuntimeRefModelMapper {

    final ObjectMapper objectMapper;

    public ClientRuntimeRefModel fromRow(final Row row) {
        final var clientRuntimeRef = new ClientRuntimeRefModel();
        clientRuntimeRef.setId(row.getLong("id"));
        clientRuntimeRef.setIdempotencyKey(row.getString("idempotency_key"));
        clientRuntimeRef.setClientId(row.getLong("client_id"));
        clientRuntimeRef.setCreated(row.getOffsetDateTime("created").toInstant());
        clientRuntimeRef.setModified(row.getOffsetDateTime("modified").toInstant());
        clientRuntimeRef.setRuntimeId(row.getLong("runtime_id"));
        clientRuntimeRef.setDeleted(row.getBoolean("deleted"));
        return clientRuntimeRef;
    }
}
