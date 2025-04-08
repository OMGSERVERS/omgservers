package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        return tenantStage;
    }
}
