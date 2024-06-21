package com.omgservers.service.module.root.impl.mappers;

import com.omgservers.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.model.rootEntityRef.RootEntityRefQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootEntityRefModelMapper {

    public RootEntityRefModel fromRow(final Row row) {
        final var rootEntityRef = new RootEntityRefModel();
        rootEntityRef.setId(row.getLong("id"));
        rootEntityRef.setIdempotencyKey(row.getString("idempotency_key"));
        rootEntityRef.setRootId(row.getLong("root_id"));
        rootEntityRef.setCreated(row.getOffsetDateTime("created").toInstant());
        rootEntityRef.setModified(row.getOffsetDateTime("modified").toInstant());
        rootEntityRef.setQualifier(RootEntityRefQualifierEnum.valueOf(row.getString("qualifier")));
        rootEntityRef.setEntityId(row.getLong("entity_id"));
        rootEntityRef.setDeleted(row.getBoolean("deleted"));
        return rootEntityRef;
    }
}
