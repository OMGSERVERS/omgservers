package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleStageResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClosedDeploymentsOperationImpl implements DeleteClosedDeploymentsOperation {

    static private final long GRACEFUL_INTERVAL = 16;

    @Override
    public void execute(final FetchStageResult fetchStageResult,
                        final HandleStageResult handleStageResult) {

        final var tenantDeploymentResourcesToDelete = fetchStageResult.tenantDeploymentResources().stream()
                .filter(tenantDeploymentResourceModel ->
                        tenantDeploymentResourceModel.getStatus().equals(TenantDeploymentResourceStatusEnum.CLOSED))
                .filter(tenantDeploymentResourceModel -> {
                    final var tenantDeploymentResourceModified = tenantDeploymentResourceModel.getModified();
                    final var tenantDeploymentCurrentInterval = Duration.between(tenantDeploymentResourceModified,
                            Instant.now());
                    return tenantDeploymentCurrentInterval.toSeconds() > GRACEFUL_INTERVAL;
                })
                .toList();

        handleStageResult.tenantDeploymentResourcesToDelete()
                .addAll(tenantDeploymentResourcesToDelete);
    }
}
