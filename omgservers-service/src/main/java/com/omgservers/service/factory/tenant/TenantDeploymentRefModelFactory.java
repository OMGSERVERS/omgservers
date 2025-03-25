package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantDeploymentRefModel create(final Long tenantId,
                                           final Long tenantStageId,
                                           final Long tenantVersionId) {
        final var id = generateIdOperation.generateId();
        final var deploymentId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentId, idempotencyKey);
    }

    public TenantDeploymentRefModel create(final Long tenantId,
                                           final Long tenantStageId,
                                           final Long tenantVersionId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var deploymentId = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentId, idempotencyKey);
    }

    public TenantDeploymentRefModel create(final Long tenantId,
                                           final Long tenantStageId,
                                           final Long tenantVersionId,
                                           final Long deploymentId,
                                           final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentId, idempotencyKey);
    }

    public TenantDeploymentRefModel create(final Long id,
                                           final Long tenantId,
                                           final Long tenantStageId,
                                           final Long tenantVersionId,
                                           final Long deploymentId,
                                           final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantDeploymentRef = new TenantDeploymentRefModel();
        tenantDeploymentRef.setId(id);
        tenantDeploymentRef.setIdempotencyKey(idempotencyKey);
        tenantDeploymentRef.setTenantId(tenantId);
        tenantDeploymentRef.setStageId(tenantStageId);
        tenantDeploymentRef.setVersionId(tenantVersionId);
        tenantDeploymentRef.setCreated(now);
        tenantDeploymentRef.setModified(now);
        tenantDeploymentRef.setDeploymentId(deploymentId);
        tenantDeploymentRef.setDeleted(false);
        return tenantDeploymentRef;
    }
}
