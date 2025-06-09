package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
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
    public void execute(final FetchTenantStageResult fetchTenantStageResult,
                        final HandleTenantStageResult handleTenantStageResult) {

        final var tenantDeploymentResourcesToDelete = fetchTenantStageResult.tenantStageState()
                .getTenantDeploymentResources().stream()
                .filter(tenantDeploymentResource ->
                        tenantDeploymentResource.getStatus().equals(TenantDeploymentResourceStatusEnum.CLOSED))
                .filter(tenantDeploymentResource -> {
                    final var tenantDeploymentResourceModified = tenantDeploymentResource.getModified();
                    final var tenantDeploymentCurrentInterval = Duration.between(tenantDeploymentResourceModified,
                            Instant.now());
                    return tenantDeploymentCurrentInterval.toSeconds() > GRACEFUL_INTERVAL;
                })
                .toList();

        handleTenantStageResult.tenantStageChangeOfState().getTenantDeploymentResourcesToDelete()
                .addAll(tenantDeploymentResourcesToDelete);
    }
}
