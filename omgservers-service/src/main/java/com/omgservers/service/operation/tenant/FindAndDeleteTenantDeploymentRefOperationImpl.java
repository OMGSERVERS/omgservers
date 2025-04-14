package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefResponse;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindAndDeleteTenantDeploymentRefOperationImpl implements FindAndDeleteTenantDeploymentRefOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long deploymentId) {
        return findTenantDeploymentRef(tenantId, deploymentId)
                .flatMap(tenantDeploymentRef -> {
                    final var tenantDeploymentRefId = tenantDeploymentRef.getId();
                    return deleteTenantDeploymentRef(tenantId, tenantDeploymentRefId);
                })
                .replaceWithVoid();

    }

    Uni<TenantDeploymentRefModel> findTenantDeploymentRef(final Long tenantId, final Long deploymentId) {
        final var request = new FindTenantDeploymentRefRequest(tenantId, deploymentId);
        return tenantShard.getService().execute(request)
                .map(FindTenantDeploymentRefResponse::getTenantDeploymentRef);
    }

    Uni<Boolean> deleteTenantDeploymentRef(final Long tenantId, final Long id) {
        final var request = new DeleteTenantDeploymentRefRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantDeploymentRefResponse::getDeleted);
    }
}
