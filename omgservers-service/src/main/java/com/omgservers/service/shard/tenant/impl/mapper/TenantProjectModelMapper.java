package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.project.TenantProjectConfigDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantProjectModelMapper {

    final ObjectMapper objectMapper;

    public TenantProjectModel execute(final Row row) {
        final var tenantProject = new TenantProjectModel();
        tenantProject.setId(row.getLong("id"));
        tenantProject.setTenantId(row.getLong("tenant_id"));
        tenantProject.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantProject.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantProject.setIdempotencyKey(row.getString("idempotency_key"));
        tenantProject.setDeleted(row.getBoolean("deleted"));
        try {
            tenantProject.setConfig(objectMapper.readValue(row.getString("config"),
                    TenantProjectConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant project config can't be parsed, tenantProject=" + tenantProject, e);
        }
        return tenantProject;
    }
}
