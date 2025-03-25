package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentResourceModelMapper {

    public TenantDeploymentResourceModel execute(final Row row) {
        final var tenantDeploymentResource = new TenantDeploymentResourceModel();
        tenantDeploymentResource.setId(row.getLong("id"));
        tenantDeploymentResource.setIdempotencyKey(row.getString("idempotency_key"));
        tenantDeploymentResource.setTenantId(row.getLong("tenant_id"));
        tenantDeploymentResource.setStageId(row.getLong("stage_id"));
        tenantDeploymentResource.setVersionId(row.getLong("version_id"));
        tenantDeploymentResource.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantDeploymentResource.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantDeploymentResource.setDeploymentId(row.getLong("deployment_id"));
        tenantDeploymentResource.setStatus(TenantDeploymentResourceStatusEnum.valueOf(row.getString("status")));
        tenantDeploymentResource.setDeleted(row.getBoolean("deleted"));
        return tenantDeploymentResource;
    }
}
