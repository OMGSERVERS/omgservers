package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeServerContainerRef.RuntimeServerContainerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeServerContainerRefModelMapper {

    final ObjectMapper objectMapper;

    public RuntimeServerContainerRefModel fromRow(final Row row) {
        final var runtimeServerContainerRef = new RuntimeServerContainerRefModel();
        runtimeServerContainerRef.setId(row.getLong("id"));
        runtimeServerContainerRef.setIdempotencyKey(row.getString("idempotency_key"));
        runtimeServerContainerRef.setRuntimeId(row.getLong("runtime_id"));
        runtimeServerContainerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeServerContainerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimeServerContainerRef.setServerId(row.getLong("server_id"));
        runtimeServerContainerRef.setServerContainerId(row.getLong("server_container_id"));
        runtimeServerContainerRef.setDeleted(row.getBoolean("deleted"));
        return runtimeServerContainerRef;
    }
}
