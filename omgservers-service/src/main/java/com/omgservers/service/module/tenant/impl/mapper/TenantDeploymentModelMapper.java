package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentModelMapper {

    public TenantDeploymentModel fromRow(final Row row) {
        final var tenantDeployment = new TenantDeploymentModel();
        tenantDeployment.setId(row.getLong("id"));
        tenantDeployment.setIdempotencyKey(row.getString("idempotency_key"));
        tenantDeployment.setTenantId(row.getLong("tenant_id"));
        tenantDeployment.setStageId(row.getLong("stage_id"));
        tenantDeployment.setVersionId(row.getLong("version_id"));
        tenantDeployment.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantDeployment.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantDeployment.setDeleted(row.getBoolean("deleted"));
        return tenantDeployment;
    }
}
