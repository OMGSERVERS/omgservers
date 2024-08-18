package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
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
public class PoolRequestModelMapper {

    final ObjectMapper objectMapper;

    public PoolRequestModel fromRow(final Row row) {
        final var poolRequest = new PoolRequestModel();
        poolRequest.setId(row.getLong("id"));
        poolRequest.setIdempotencyKey(row.getString("idempotency_key"));
        poolRequest.setPoolId(row.getLong("pool_id"));
        poolRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        poolRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        poolRequest.setRuntimeId(row.getLong("runtime_id"));
        poolRequest.setRuntimeQualifier(RuntimeQualifierEnum.valueOf(row.getString("runtime_qualifier")));
        poolRequest.setDeleted(row.getBoolean("deleted"));
        try {
            poolRequest.setConfig(objectMapper.readValue(row.getString("config"),
                    PoolRequestConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "pool runtime server container request config can't be parsed, " +
                            "poolRequest=" + poolRequest, e);
        }
        return poolRequest;
    }
}
