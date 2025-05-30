package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleStageResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ClosePreviouslyCreatedDeploymentsOperationImpl implements ClosePreviouslyCreatedDeploymentsOperation {

    @Override
    public void execute(final FetchStageResult fetchStageResult,
                        final HandleStageResult handleStageResult) {
        final var tenantStage = fetchStageResult.tenantStage();

        final var createdDeploymentResources = fetchStageResult.tenantDeploymentResources().stream()
                .filter(tenantDeploymentResource ->
                        tenantDeploymentResource.getStatus().equals(TenantDeploymentResourceStatusEnum.CREATED))
                .toList();

        if (createdDeploymentResources.size() > 1) {
            final var previouslyCreatedDeployments = createdDeploymentResources
                    .subList(0, createdDeploymentResources.size() - 1);

            log.info("\"{}\" previously created deployments of stage \"{}\" in tenant \"{}\" is marked as closed",
                    previouslyCreatedDeployments.size(),
                    tenantStage.getId(),
                    tenantStage.getTenantId());

            handleStageResult.tenantDeploymentResourcesToClose()
                    .addAll(previouslyCreatedDeployments);
        }
    }
}
