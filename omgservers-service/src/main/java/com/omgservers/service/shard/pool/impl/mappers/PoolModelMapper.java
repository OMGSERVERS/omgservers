package com.omgservers.service.shard.pool.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.pool.PoolConfigDto;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolModelMapper {

    final ObjectMapper objectMapper;

    public PoolModel execute(final Row row) {
        final var pool = new PoolModel();
        pool.setId(row.getLong("id"));
        pool.setIdempotencyKey(row.getString("idempotency_key"));
        pool.setCreated(row.getOffsetDateTime("created").toInstant());
        pool.setModified(row.getOffsetDateTime("modified").toInstant());
        pool.setDeleted(row.getBoolean("deleted"));
        try {
            pool.setConfig(objectMapper.readValue(row.getString("config"), PoolConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "pool config can't be parsed, pool=" + pool, e);
        }
        return pool;
    }
}
