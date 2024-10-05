package com.omgservers.service.module.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantFilesArchiveProjectionModelMapper {

    final ObjectMapper objectMapper;

    public TenantFilesArchiveProjectionModel fromRow(final Row row) {
        final var tenantFilesArchiveProjection = new TenantFilesArchiveProjectionModel();
        tenantFilesArchiveProjection.setId(row.getLong("id"));
        tenantFilesArchiveProjection.setIdempotencyKey(row.getString("idempotency_key"));
        tenantFilesArchiveProjection.setTenantId(row.getLong("tenant_id"));
        tenantFilesArchiveProjection.setVersionId(row.getLong("version_id"));
        tenantFilesArchiveProjection.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantFilesArchiveProjection.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantFilesArchiveProjection.setDeleted(row.getBoolean("deleted"));

        return tenantFilesArchiveProjection;
    }
}
