package com.omgservers.service.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.model.entitiy.EntityQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EntityModelMapper {

    final ObjectMapper objectMapper;

    public EntityModel fromRow(Row row) {
        final var entity = new EntityModel();
        entity.setId(row.getLong("id"));
        entity.setCreated(row.getOffsetDateTime("created").toInstant());
        entity.setModified(row.getOffsetDateTime("modified").toInstant());
        entity.setEntityId(row.getLong("entity_id"));
        entity.setQualifier(EntityQualifierEnum.valueOf(row.getString("qualifier")));
        entity.setDeleted(row.getBoolean("deleted"));
        return entity;
    }
}
