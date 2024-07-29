package com.omgservers.service.module.root.impl.mappers;

import com.omgservers.schema.model.root.RootModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootModelMapper {

    public RootModel fromRow(final Row row) {
        final var root = new RootModel();
        root.setId(row.getLong("id"));
        root.setIdempotencyKey(row.getString("idempotency_key"));
        root.setCreated(row.getOffsetDateTime("created").toInstant());
        root.setModified(row.getOffsetDateTime("modified").toInstant());
        root.setDeleted(row.getBoolean("deleted"));
        return root;
    }
}
