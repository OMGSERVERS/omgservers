package com.omgservers.service.module.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.poolServer.PoolServerConfigDto;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerModelMapper {

    final ObjectMapper objectMapper;

    public PoolServerModel fromRow(final Row row) {
        final var poolServer = new PoolServerModel();
        poolServer.setId(row.getLong("id"));
        poolServer.setIdempotencyKey(row.getString("idempotency_key"));
        poolServer.setPoolId(row.getLong("pool_id"));
        poolServer.setCreated(row.getOffsetDateTime("created").toInstant());
        poolServer.setModified(row.getOffsetDateTime("modified").toInstant());
        poolServer.setQualifier(PoolServerQualifierEnum.valueOf(row.getString("qualifier")));
        poolServer.setDeleted(row.getBoolean("deleted"));
        try {
            poolServer.setConfig(objectMapper
                    .readValue(row.getString("config"), PoolServerConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "poo server config can't be parsed, poolServer=" + poolServer, e);
        }
        return poolServer;
    }
}
