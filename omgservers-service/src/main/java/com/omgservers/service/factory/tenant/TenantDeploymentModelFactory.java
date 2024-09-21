package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantDeploymentModel create(final Long tenantId,
                                        final Long stageId,
                                        final Long versionId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, stageId, versionId, idempotencyKey);
    }

    public TenantDeploymentModel create(final Long tenantId,
                                        final Long stageId,
                                        final Long versionId,
                                        final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, stageId, versionId, idempotencyKey);
    }

    public TenantDeploymentModel create(final Long id,
                                        final Long tenantId,
                                        final Long stageId,
                                        final Long versionId,
                                        final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantDeployment = new TenantDeploymentModel();
        tenantDeployment.setId(id);
        tenantDeployment.setIdempotencyKey(idempotencyKey);
        tenantDeployment.setTenantId(tenantId);
        tenantDeployment.setStageId(stageId);
        tenantDeployment.setVersionId(versionId);
        tenantDeployment.setCreated(now);
        tenantDeployment.setModified(now);
        tenantDeployment.setDeleted(false);
        return tenantDeployment;
    }
}
