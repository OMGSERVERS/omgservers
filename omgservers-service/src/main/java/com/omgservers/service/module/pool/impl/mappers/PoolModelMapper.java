package com.omgservers.service.module.pool.impl.mappers;

import com.omgservers.schema.model.pool.PoolModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolModelMapper {

    public PoolModel execute(final Row row) {
        final var pool = new PoolModel();
        pool.setId(row.getLong("id"));
        pool.setIdempotencyKey(row.getString("idempotency_key"));
        pool.setCreated(row.getOffsetDateTime("created").toInstant());
        pool.setModified(row.getOffsetDateTime("modified").toInstant());
        pool.setDeleted(row.getBoolean("deleted"));
        return pool;
    }
}
