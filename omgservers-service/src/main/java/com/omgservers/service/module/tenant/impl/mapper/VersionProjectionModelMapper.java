package com.omgservers.service.module.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.version.VersionProjectionModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionProjectionModelMapper {

    final ObjectMapper objectMapper;

    public VersionProjectionModel fromRow(Row row) {
        final var versionProjection = new VersionProjectionModel();
        versionProjection.setId(row.getLong("id"));
        versionProjection.setTenantId(row.getLong("tenant_id"));
        versionProjection.setStageId(row.getLong("stage_id"));
        versionProjection.setCreated(row.getOffsetDateTime("created").toInstant());
        versionProjection.setModified(row.getOffsetDateTime("modified").toInstant());
        versionProjection.setDeleted(row.getBoolean("deleted"));
        return versionProjection;
    }
}
