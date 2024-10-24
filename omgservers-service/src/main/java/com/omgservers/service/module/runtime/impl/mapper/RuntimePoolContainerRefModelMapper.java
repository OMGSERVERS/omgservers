package com.omgservers.service.module.runtime.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePoolContainerRefModelMapper {

    final ObjectMapper objectMapper;

    public RuntimePoolContainerRefModel execute(final Row row) {
        final var runtimePoolContainerRefModel = new RuntimePoolContainerRefModel();
        runtimePoolContainerRefModel.setId(row.getLong("id"));
        runtimePoolContainerRefModel.setIdempotencyKey(row.getString("idempotency_key"));
        runtimePoolContainerRefModel.setRuntimeId(row.getLong("runtime_id"));
        runtimePoolContainerRefModel.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimePoolContainerRefModel.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimePoolContainerRefModel.setPoolId(row.getLong("pool_id"));
        runtimePoolContainerRefModel.setServerId(row.getLong("server_id"));
        runtimePoolContainerRefModel.setContainerId(row.getLong("container_id"));
        runtimePoolContainerRefModel.setDeleted(row.getBoolean("deleted"));
        return runtimePoolContainerRefModel;
    }
}
