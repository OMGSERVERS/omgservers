package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetTenantVersionConfigOperationImpl implements GetTenantVersionConfigOperation {

    final MatchmakerShard matchmakerShard;
    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    @Override
    public Uni<TenantVersionConfigDto> execute(final Long deploymentId) {
        return getDeployment(deploymentId)
                .flatMap(deployment -> {
                    final var tenantId = deployment.getTenantId();
                    final var tenantVersionId = deployment.getVersionId();
                    return getTenantVersionConfig(tenantId, tenantVersionId);
                });
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }

    Uni<TenantVersionConfigDto> getTenantVersionConfig(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionConfigRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionConfigResponse::getTenantVersionConfig);
    }
}
