package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentModel create(final Long tenantId,
                                  final Long tenantStageId,
                                  final Long tenantVersionId,
                                  final DeploymentConfigDto deploymentConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentConfig, idempotencyKey);
    }

    public DeploymentModel create(final Long tenantId,
                                  final Long tenantStageId,
                                  final Long tenantVersionId,
                                  final DeploymentConfigDto deploymentConfig,
                                  final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, tenantStageId, tenantVersionId, deploymentConfig, idempotencyKey);
    }

    public DeploymentModel create(final Long id,
                                  final Long tenantId,
                                  final Long tenantStageId,
                                  final Long tenantVersionId,
                                  final DeploymentConfigDto deploymentConfig,
                                  final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deployment = new DeploymentModel();
        deployment.setId(id);
        deployment.setIdempotencyKey(idempotencyKey);
        deployment.setCreated(now);
        deployment.setModified(now);
        deployment.setTenantId(tenantId);
        deployment.setStageId(tenantStageId);
        deployment.setVersionId(tenantVersionId);
        deployment.setConfig(deploymentConfig);
        deployment.setDeleted(false);
        return deployment;
    }
}
