package com.omgservers.service.module.pool.impl.mappers;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerRefModelMapper {

    public PoolServerRefModel fromRow(final Row row) {
        final var poolServerRef = new PoolServerRefModel();
        poolServerRef.setId(row.getLong("id"));
        poolServerRef.setIdempotencyKey(row.getString("idempotency_key"));
        poolServerRef.setPoolId(row.getLong("pool_id"));
        poolServerRef.setCreated(row.getOffsetDateTime("created").toInstant());
        poolServerRef.setModified(row.getOffsetDateTime("modified").toInstant());
        poolServerRef.setServerId(row.getLong("server_id"));
        poolServerRef.setDeleted(row.getBoolean("deleted"));
        return poolServerRef;
    }
}
