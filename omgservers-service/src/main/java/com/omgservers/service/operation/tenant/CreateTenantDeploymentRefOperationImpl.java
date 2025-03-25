package com.omgservers.service.operation.tenant;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.SyncTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.SyncTenantDeploymentRefResponse;
import com.omgservers.service.factory.tenant.TenantDeploymentRefModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantDeploymentRefOperationImpl implements CreateTenantDeploymentRefOperation {

    final TenantShard tenantShard;

    final TenantDeploymentRefModelFactory tenantDeploymentRefModelFactory;

    @Override
    public Uni<Boolean> execute(final Long tenantId,
                                final Long tenantStageId,
                                final Long tenantVersionId,
                                final Long deploymentId,
                                final String idempotencyKey) {
        final var tenantDeploymentRef = tenantDeploymentRefModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId,
                deploymentId,
                idempotencyKey);

        final var request = new SyncTenantDeploymentRefRequest(tenantDeploymentRef);
        return tenantShard.getService().execute(request)
                .map(SyncTenantDeploymentRefResponse::getCreated);
    }
}
