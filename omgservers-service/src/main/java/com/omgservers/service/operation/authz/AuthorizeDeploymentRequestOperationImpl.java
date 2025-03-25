package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizeDeploymentRequestOperationImpl implements AuthorizeDeploymentRequestOperation {

    final DeploymentShard deploymentShard;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    @Override
    public Uni<DeploymentAuthorization> execute(final Long deploymentId,
                                                final Long userId,
                                                final TenantStagePermissionQualifierEnum permission) {
        return getDeployment(deploymentId)
                .flatMap(deployment -> {
                    final var tenantId = deployment.getTenantId();
                    final var tenantStageId = deployment.getStageId();
                    return checkTenantStagePermissionOperation.execute(tenantId,
                                    tenantStageId,
                                    userId,
                                    permission)
                            .map(voidItem -> new DeploymentAuthorization(deploymentId,
                                    userId,
                                    tenantId,
                                    tenantStageId,
                                    permission));
                });
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }
}
