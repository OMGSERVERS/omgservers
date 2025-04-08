package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenant.TenantModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantModelMapper {

    final ObjectMapper objectMapper;

    public TenantModel execute(final Row row) {
        final var tenant = new TenantModel();
        tenant.setId(row.getLong("id"));
        tenant.setCreated(row.getOffsetDateTime("created").toInstant());
        tenant.setModified(row.getOffsetDateTime("modified").toInstant());
        tenant.setIdempotencyKey(row.getString("idempotency_key"));
        tenant.setDeleted(row.getBoolean("deleted"));
        return tenant;
    }
}
