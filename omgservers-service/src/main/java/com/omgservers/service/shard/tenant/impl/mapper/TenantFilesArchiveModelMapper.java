package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantFilesArchiveModelMapper {

    final ObjectMapper objectMapper;

    public TenantFilesArchiveModel fromRow(final Row row) {
        final var tenantFilesArchive = new TenantFilesArchiveModel();
        tenantFilesArchive.setId(row.getLong("id"));
        tenantFilesArchive.setIdempotencyKey(row.getString("idempotency_key"));
        tenantFilesArchive.setTenantId(row.getLong("tenant_id"));
        tenantFilesArchive.setVersionId(row.getLong("version_id"));
        tenantFilesArchive.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantFilesArchive.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantFilesArchive.setDeleted(row.getBoolean("deleted"));
        tenantFilesArchive.setBase64Archive(Base64.getEncoder()
                .encodeToString(row.getBuffer("archive").getBytes()));

        return tenantFilesArchive;
    }
}
