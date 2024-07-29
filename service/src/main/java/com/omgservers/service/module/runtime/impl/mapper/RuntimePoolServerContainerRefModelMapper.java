package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePoolServerContainerRefModelMapper {

    final ObjectMapper objectMapper;

    public RuntimePoolServerContainerRefModel fromRow(final Row row) {
        final var runtimePoolServerContainerRef = new RuntimePoolServerContainerRefModel();
        runtimePoolServerContainerRef.setId(row.getLong("id"));
        runtimePoolServerContainerRef.setIdempotencyKey(row.getString("idempotency_key"));
        runtimePoolServerContainerRef.setRuntimeId(row.getLong("runtime_id"));
        runtimePoolServerContainerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimePoolServerContainerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimePoolServerContainerRef.setPoolId(row.getLong("pool_id"));
        runtimePoolServerContainerRef.setServerId(row.getLong("server_id"));
        runtimePoolServerContainerRef.setContainerId(row.getLong("container_id"));
        runtimePoolServerContainerRef.setDeleted(row.getBoolean("deleted"));
        return runtimePoolServerContainerRef;
    }
}
