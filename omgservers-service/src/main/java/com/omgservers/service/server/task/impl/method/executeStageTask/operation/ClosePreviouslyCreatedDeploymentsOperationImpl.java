package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageDeploymentResourceToUpdateStatusDto;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ClosePreviouslyCreatedDeploymentsOperationImpl implements ClosePreviouslyCreatedDeploymentsOperation {

    @Override
    public void execute(final FetchTenantStageResult fetchTenantStageResult,
                        final HandleTenantStageResult handleTenantStageResult) {
        final var tenantStage = fetchTenantStageResult.tenantStageState().getTenantStage();

        final var createdDeploymentResources = fetchTenantStageResult.tenantStageState()
                .getTenantDeploymentResources().stream()
                .filter(tenantDeploymentResource ->
                        tenantDeploymentResource.getStatus().equals(TenantDeploymentResourceStatusEnum.CREATED))
                .toList();

        if (createdDeploymentResources.size() > 1) {
            final var previouslyCreatedDeployments = createdDeploymentResources
                    .subList(0, createdDeploymentResources.size() - 1);

            final var dtoToUpdateStatus = previouslyCreatedDeployments.stream()
                    .map(tenantDeploymentResource ->
                            new TenantStageDeploymentResourceToUpdateStatusDto(
                                    tenantDeploymentResource.getId(),
                                    TenantDeploymentResourceStatusEnum.CLOSED))
                    .toList();

            log.info("\"{}\" previously created deployments of stage \"{}\" in tenant \"{}\" is marked as closed",
                    previouslyCreatedDeployments.size(),
                    tenantStage.getId(),
                    tenantStage.getTenantId());

            handleTenantStageResult.tenantStageChangeOfState().getTenantDeploymentResourcesToUpdateStatus()
                    .addAll(dtoToUpdateStatus);
        }
    }
}
