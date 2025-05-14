package com.omgservers.service.master.entity.impl.mappers;

import com.omgservers.schema.model.entity.EntityModel;
import com.omgservers.schema.model.entity.EntityQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EntityModelMapper {

    public EntityModel execute(final Row row) {
        final var entity = new EntityModel();
        entity.setId(row.getLong("id"));
        entity.setIdempotencyKey(row.getString("idempotency_key"));
        entity.setCreated(row.getOffsetDateTime("created").toInstant());
        entity.setModified(row.getOffsetDateTime("modified").toInstant());
        entity.setQualifier(EntityQualifierEnum.valueOf(row.getString("qualifier")));
        entity.setEntityId(row.getLong("entity_id"));
        entity.setDeleted(row.getBoolean("deleted"));
        return entity;
    }
}
