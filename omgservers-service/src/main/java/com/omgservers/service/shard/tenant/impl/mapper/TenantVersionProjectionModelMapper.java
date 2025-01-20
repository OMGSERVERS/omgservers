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

    public TenantVersionProjectionModel fromRow(final Row row) {
        final var versionProjection = new TenantVersionProjectionModel();
        versionProjection.setId(row.getLong("id"));
        versionProjection.setTenantId(row.getLong("tenant_id"));
        versionProjection.setProjectId(row.getLong("project_id"));
        versionProjection.setCreated(row.getOffsetDateTime("created").toInstant());
        versionProjection.setModified(row.getOffsetDateTime("modified").toInstant());
        versionProjection.setDeleted(row.getBoolean("deleted"));
        return versionProjection;
    }
}
