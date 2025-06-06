package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchDeploymentOperationImpl implements FetchDeploymentOperation {

    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    final GetTenantVersionConfigOperation getTenantVersionConfigOperation;

    @Override
    public Uni<FetchDeploymentResult> execute(final Long deploymentId) {
        return getTenantVersionConfigOperation.execute(deploymentId)
                .flatMap(tenantVersionConfig -> getDeploymentState(deploymentId)
                        .flatMap(deploymentState -> {
                            final var tenantId = deploymentState.getDeployment().getTenantId();
                            return findTenantDeploymentResource(tenantId, deploymentId)
                                    .map(tenantDeploymentResource ->
                                            new FetchDeploymentResult(deploymentId,
                                                    tenantDeploymentResource,
                                                    tenantVersionConfig,
                                                    deploymentState));
                        }));
    }

    Uni<TenantDeploymentResourceModel> findTenantDeploymentResource(final Long tenantId,
                                                                    final Long deploymentId) {
        final var request = new FindTenantDeploymentResourceRequest(tenantId, deploymentId);
        return tenantShard.getService().execute(request)
                .map(FindTenantDeploymentResourceResponse::getTenantDeploymentResource);
    }

    Uni<DeploymentStateDto> getDeploymentState(final Long deploymentId) {
        final var request = new GetDeploymentStateRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentStateResponse::getDeploymentState);
    }
}
