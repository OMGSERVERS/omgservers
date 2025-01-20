package com.omgservers.service.shard.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerConfigDto;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolContainerModelMapper {

    final ObjectMapper objectMapper;

    public PoolContainerModel execute(final Row row) {
        final var poolContainer = new PoolContainerModel();
        poolContainer.setId(row.getLong("id"));
        poolContainer.setIdempotencyKey(row.getString("idempotency_key"));
        poolContainer.setPoolId(row.getLong("pool_id"));
        poolContainer.setServerId(row.getLong("server_id"));
        poolContainer.setCreated(row.getOffsetDateTime("created").toInstant());
        poolContainer.setModified(row.getOffsetDateTime("modified").toInstant());
        poolContainer.setRuntimeId(row.getLong("runtime_id"));
        poolContainer.setRuntimeQualifier(RuntimeQualifierEnum.valueOf(row.getString("runtime_qualifier")));
        poolContainer.setDeleted(row.getBoolean("deleted"));
        try {
            poolContainer.setConfig(objectMapper
                    .readValue(row.getString("config"), PoolContainerConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "poo server container config can't be parsed, poolContainer=" + poolContainer, e);
        }
        return poolContainer;
    }
}
