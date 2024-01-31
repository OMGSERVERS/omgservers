package com.omgservers.service.module.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientRuntimeModelMapper {

    final ObjectMapper objectMapper;

    public ClientRuntimeModel fromRow(Row row) {
        final var clientRuntime = new ClientRuntimeModel();
        clientRuntime.setId(row.getLong("id"));
        clientRuntime.setClientId(row.getLong("client_id"));
        clientRuntime.setCreated(row.getOffsetDateTime("created").toInstant());
        clientRuntime.setModified(row.getOffsetDateTime("modified").toInstant());
        clientRuntime.setRuntimeId(row.getLong("runtime_id"));
        clientRuntime.setDeleted(row.getBoolean("deleted"));

        return clientRuntime;
    }
}
