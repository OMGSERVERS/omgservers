package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestConfigModel;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
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
public class PoolRuntimeServerContainerRequestModelMapper {

    final ObjectMapper objectMapper;

    public PoolRuntimeServerContainerRequestModel fromRow(final Row row) {
        final var poolRuntimeServerContainerRequest = new PoolRuntimeServerContainerRequestModel();
        poolRuntimeServerContainerRequest.setId(row.getLong("id"));
        poolRuntimeServerContainerRequest.setIdempotencyKey(row.getString("idempotency_key"));
        poolRuntimeServerContainerRequest.setPoolId(row.getLong("pool_id"));
        poolRuntimeServerContainerRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        poolRuntimeServerContainerRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        poolRuntimeServerContainerRequest.setRuntimeId(row.getLong("runtime_id"));
        poolRuntimeServerContainerRequest.setDeleted(row.getBoolean("deleted"));
        try {
            poolRuntimeServerContainerRequest.setConfig(objectMapper.readValue(row.getString("config"),
                    PoolRuntimeServerContainerRequestConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "pool runtime server container request config can't be parsed, " +
                            "poolRuntimeServerContainerRequest=" + poolRuntimeServerContainerRequest, e);
        }
        return poolRuntimeServerContainerRequest;
    }
}
