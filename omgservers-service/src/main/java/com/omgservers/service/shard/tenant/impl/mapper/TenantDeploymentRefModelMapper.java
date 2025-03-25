package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentRefModelMapper {

    public TenantDeploymentRefModel execute(final Row row) {
        final var tenantDeploymentRef = new TenantDeploymentRefModel();
        tenantDeploymentRef.setId(row.getLong("id"));
        tenantDeploymentRef.setIdempotencyKey(row.getString("idempotency_key"));
        tenantDeploymentRef.setTenantId(row.getLong("tenant_id"));
        tenantDeploymentRef.setStageId(row.getLong("stage_id"));
        tenantDeploymentRef.setVersionId(row.getLong("version_id"));
        tenantDeploymentRef.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantDeploymentRef.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantDeploymentRef.setDeploymentId(row.getLong("deployment_id"));
        tenantDeploymentRef.setDeleted(row.getBoolean("deleted"));
        return tenantDeploymentRef;
    }
}
