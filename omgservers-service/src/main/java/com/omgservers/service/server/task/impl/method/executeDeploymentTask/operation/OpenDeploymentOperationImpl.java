package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.operation.tenant.CreateOpenDeploymentTenantStageCommandOperation;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class OpenDeploymentOperationImpl implements OpenDeploymentOperation {

    final CreateOpenDeploymentTenantStageCommandOperation createOpenDeploymentTenantStageCommandOperation;

    @Override
    public Uni<Void> execute(final FetchDeploymentResult fetchDeploymentResult) {
        final var tenantId = fetchDeploymentResult.deploymentState().getDeployment().getTenantId();
        final var tenantStageId = fetchDeploymentResult.deploymentState().getDeployment().getStageId();
        final var deploymentId = fetchDeploymentResult.deploymentId();
        return createOpenDeploymentTenantStageCommandOperation.execute(tenantId, tenantStageId, deploymentId)
                .invoke(created -> {
                    if (created) {
                        log.info("Created open deployment \"{}\" command", deploymentId);
                    }
                })
                .replaceWithVoid();
    }
}
