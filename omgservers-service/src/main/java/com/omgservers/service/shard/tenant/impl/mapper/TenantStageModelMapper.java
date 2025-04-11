package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantStageModelMapper {

    final ObjectMapper objectMapper;

    public TenantStageModel execute(final Row row) {
        final var tenantStage = new TenantStageModel();
        tenantStage.setId(row.getLong("id"));
        tenantStage.setTenantId(row.getLong("tenant_id"));
        tenantStage.setProjectId(row.getLong("project_id"));
        tenantStage.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantStage.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantStage.setIdempotencyKey(row.getString("idempotency_key"));
        tenantStage.setDeleted(row.getBoolean("deleted"));
        try {
            tenantStage.setConfig(objectMapper.readValue(row.getString("config"),
                    TenantStageConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant stage config can't be parsed, tenantStage=" + tenantStage, e);
        }
        return tenantStage;
    }
}
