package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantDeploymentResourceModel create(final Long tenantId,
                                                final Long tenantStageId,
                                                final Long tenantVersionId) {
        final var id = generateIdOperation.generateId();
        final var deploymentId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentId, idempotencyKey);
    }

    public TenantDeploymentResourceModel create(final Long tenantId,
                                                final Long tenantStageId,
                                                final Long tenantVersionId,
                                                final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var deploymentId = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentId, idempotencyKey);
    }

    public TenantDeploymentResourceModel create(final Long id,
                                                final Long tenantId,
                                                final Long tenantStageId,
                                                final Long tenantVersionId,
                                                final Long deploymentId,
                                                final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantDeploymentResource = new TenantDeploymentResourceModel();
        tenantDeploymentResource.setId(id);
        tenantDeploymentResource.setIdempotencyKey(idempotencyKey);
        tenantDeploymentResource.setTenantId(tenantId);
        tenantDeploymentResource.setStageId(tenantStageId);
        tenantDeploymentResource.setVersionId(tenantVersionId);
        tenantDeploymentResource.setCreated(now);
        tenantDeploymentResource.setModified(now);
        tenantDeploymentResource.setDeploymentId(deploymentId);
        tenantDeploymentResource.setStatus(TenantDeploymentResourceStatusEnum.PENDING);
        tenantDeploymentResource.setDeleted(false);
        return tenantDeploymentResource;
    }
}
