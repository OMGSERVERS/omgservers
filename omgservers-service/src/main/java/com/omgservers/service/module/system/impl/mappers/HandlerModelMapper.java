package com.omgservers.service.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.handler.HandlerModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HandlerModelMapper {

    final ObjectMapper objectMapper;

    public HandlerModel fromRow(Row row) {
        final var handler = new HandlerModel();
        handler.setId(row.getLong("id"));
        handler.setCreated(row.getOffsetDateTime("created").toInstant());
        handler.setModified(row.getOffsetDateTime("modified").toInstant());
        handler.setDeleted(row.getBoolean("deleted"));
        return handler;
    }
}
