package com.omgservers.service.shard.alias.impl.mappers;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasModelMapper {

    public AliasModel execute(final Row row) {
        final var alias = new AliasModel();
        alias.setId(row.getLong("id"));
        alias.setIdempotencyKey(row.getString("idempotency_key"));
        alias.setCreated(row.getOffsetDateTime("created").toInstant());
        alias.setModified(row.getOffsetDateTime("modified").toInstant());
        alias.setQualifier(AliasQualifierEnum.valueOf(row.getString("qualifier")));
        alias.setShardKey(row.getString("shard_key"));
        alias.setUniquenessGroup(row.getLong("uniqueness_group"));
        alias.setEntityId(row.getLong("entity_id"));
        alias.setValue(row.getString("alias_value"));
        alias.setDeleted(row.getBoolean("deleted"));
        return alias;
    }
}
