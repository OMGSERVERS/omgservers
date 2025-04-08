package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantVersionProjectionModelMapper {

    final ObjectMapper objectMapper;

    public TenantVersionProjectionModel execute(final Row row) {
        final var tenantVersionProjection = new TenantVersionProjectionModel();
        tenantVersionProjection.setId(row.getLong("id"));
        tenantVersionProjection.setTenantId(row.getLong("tenant_id"));
        tenantVersionProjection.setProjectId(row.getLong("project_id"));
        tenantVersionProjection.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantVersionProjection.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantVersionProjection.setDeleted(row.getBoolean("deleted"));
        return tenantVersionProjection;
    }
}
