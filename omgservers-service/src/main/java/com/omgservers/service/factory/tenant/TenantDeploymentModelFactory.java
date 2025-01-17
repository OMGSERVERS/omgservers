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
                                        final Long tenantStageId,
                                        final Long tenantVersionId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantStageId, tenantVersionId, idempotencyKey);
    }

    public TenantDeploymentModel create(final Long tenantId,
                                        final Long tenantStageId,
                                        final Long tenantVersionId,
                                        final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, tenantVersionId, idempotencyKey);
    }

    public TenantDeploymentModel create(final Long id,
                                        final Long tenantId,
                                        final Long tenantStageId,
                                        final Long tenantVersionId,
                                        final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        final var queueId = generateIdOperation.generateId();

        final var tenantDeployment = new TenantDeploymentModel();
        tenantDeployment.setId(id);
        tenantDeployment.setIdempotencyKey(idempotencyKey);
        tenantDeployment.setTenantId(tenantId);
        tenantDeployment.setStageId(tenantStageId);
        tenantDeployment.setVersionId(tenantVersionId);
        tenantDeployment.setCreated(now);
        tenantDeployment.setModified(now);
        tenantDeployment.setQueueId(queueId);
        tenantDeployment.setDeleted(false);
        return tenantDeployment;
    }
}
