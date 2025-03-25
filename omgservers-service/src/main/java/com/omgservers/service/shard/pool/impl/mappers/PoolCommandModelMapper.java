package com.omgservers.service.shard.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.model.poolCommand.PoolCommandQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolCommandModelMapper {

    final ObjectMapper objectMapper;

    public PoolCommandModel execute(final Row row) {
        final var poolCommand = new PoolCommandModel();
        poolCommand.setId(row.getLong("id"));
        poolCommand.setIdempotencyKey(row.getString("idempotency_key"));
        poolCommand.setPoolId(row.getLong("pool_id"));
        poolCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        poolCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = PoolCommandQualifierEnum.valueOf(row.getString("qualifier"));
        poolCommand.setQualifier(qualifier);
        poolCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"),
                    qualifier.getBodyClass());
            poolCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "pool command can't be parsed, poolCommand=" + poolCommand, e);
        }
        return poolCommand;
    }
}
