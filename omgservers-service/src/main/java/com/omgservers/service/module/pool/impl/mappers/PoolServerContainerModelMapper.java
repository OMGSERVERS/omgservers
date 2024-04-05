package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.poolSeverContainer.PoolServerContainerConfigModel;
import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerContainerModelMapper {

    final ObjectMapper objectMapper;

    public PoolServerContainerModel fromRow(final Row row) {
        final var poolServerContainer = new PoolServerContainerModel();
        poolServerContainer.setId(row.getLong("id"));
        poolServerContainer.setIdempotencyKey(row.getString("idempotency_key"));
        poolServerContainer.setPoolId(row.getLong("pool_id"));
        poolServerContainer.setServerId(row.getLong("server_id"));
        poolServerContainer.setCreated(row.getOffsetDateTime("created").toInstant());
        poolServerContainer.setModified(row.getOffsetDateTime("modified").toInstant());
        poolServerContainer.setRuntimeId(row.getLong("runtime_id"));
        poolServerContainer.setDeleted(row.getBoolean("deleted"));
        try {
            poolServerContainer.setConfig(objectMapper
                    .readValue(row.getString("config"), PoolServerContainerConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "poo server container config can't be parsed, poolServerContainer=" + poolServerContainer, e);
        }
        return poolServerContainer;
    }
}
