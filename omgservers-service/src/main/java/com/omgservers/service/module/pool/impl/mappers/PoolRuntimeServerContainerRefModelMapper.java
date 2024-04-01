package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRuntimeServerContainerRefModelMapper {

    final ObjectMapper objectMapper;

    public PoolRuntimeServerContainerRefModel fromRow(final Row row) {
        final var poolRuntimeServerContainerRef = new PoolRuntimeServerContainerRefModel();
        poolRuntimeServerContainerRef.setId(row.getLong("id"));
        poolRuntimeServerContainerRef.setIdempotencyKey(row.getString("idempotency_key"));
        poolRuntimeServerContainerRef.setPoolId(row.getLong("pool_id"));
        poolRuntimeServerContainerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        poolRuntimeServerContainerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        poolRuntimeServerContainerRef.setRuntimeId(row.getLong("runtime_id"));
        poolRuntimeServerContainerRef.setServerId(row.getLong("server_id"));
        poolRuntimeServerContainerRef.setContainerId(row.getLong("container_id"));
        poolRuntimeServerContainerRef.setDeleted(row.getBoolean("deleted"));
        return poolRuntimeServerContainerRef;
    }
}
