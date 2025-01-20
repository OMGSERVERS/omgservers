package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.project.TenantProjectModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantProjectModelMapper {

    final ObjectMapper objectMapper;

    public TenantProjectModel fromRow(final Row row) {
        final var tenantProject = new TenantProjectModel();
        tenantProject.setId(row.getLong("id"));
        tenantProject.setTenantId(row.getLong("tenant_id"));
        tenantProject.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantProject.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantProject.setIdempotencyKey(row.getString("idempotency_key"));
        tenantProject.setDeleted(row.getBoolean("deleted"));

        return tenantProject;
    }
}
