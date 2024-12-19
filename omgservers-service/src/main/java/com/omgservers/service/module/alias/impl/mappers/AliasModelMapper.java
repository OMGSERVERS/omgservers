package com.omgservers.service.module.alias.impl.mappers;

import com.omgservers.schema.model.alias.AliasModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasModelMapper {

    public AliasModel fromRow(final Row row) {
        final var alias = new AliasModel();
        alias.setId(row.getLong("id"));
        alias.setIdempotencyKey(row.getString("idempotency_key"));
        alias.setCreated(row.getOffsetDateTime("created").toInstant());
        alias.setModified(row.getOffsetDateTime("modified").toInstant());
        alias.setShardKey(row.getLong("shard_key"));
        alias.setValue(row.getString("alias_value"));
        alias.setEntityId(row.getLong("entity_id"));
        alias.setDeleted(row.getBoolean("deleted"));
        return alias;
    }
}
