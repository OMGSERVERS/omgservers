package com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands.tenantStageCommandHandler;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageDeploymentResourceToUpdateStatusDto;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.schema.model.tenantStageCommand.body.OpenDeploymentTenantStageCommandBodyDto;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands.TenantStageCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenDeploymentTenantStageCommandHandlerImpl implements TenantStageCommandHandler {

    @Override
    public TenantStageCommandQualifierEnum getQualifier() {
        return TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT;
    }

    @Override
    public boolean handle(final FetchTenantStageResult fetchTenantStageResult,
                          final HandleTenantStageResult handleTenantStageResult,
                          final TenantStageCommandModel tenantStageCommand) {
        log.debug("Handle command, {}", tenantStageCommand);

        final var tenantId = fetchTenantStageResult.tenantId();
        final var tenantStageId = fetchTenantStageResult.tenantStageId();

        final var body = (OpenDeploymentTenantStageCommandBodyDto) tenantStageCommand.getBody();
        final var deploymentId = body.getDeploymentId();

        final var tenantDeploymentResourceToOpen =
                fetchTenantStageResult.tenantStageState().getTenantDeploymentResources().stream()
                        .filter(tenantDeploymentResource ->
                                tenantDeploymentResource.getDeploymentId().equals(deploymentId))
                        .filter(tenantDeploymentResource -> tenantDeploymentResource.getStatus()
                                .equals(TenantDeploymentResourceStatusEnum.PENDING))
                        .map(TenantDeploymentResourceModel::getId)
                        .toList();

        if (tenantDeploymentResourceToOpen.isEmpty()) {
            log.warn("No tenant deployment resource found to open for deploymentId=\"{}\" " +
                    "in tenant=\"{}\", skip command", tenantId, tenantStageId);
        } else {
            tenantDeploymentResourceToOpen.forEach(tenantDeploymentResourceId -> {
                final var dtoToUpdateStatus = new TenantStageDeploymentResourceToUpdateStatusDto(
                        tenantDeploymentResourceId,
                        TenantDeploymentResourceStatusEnum.CREATED);

                handleTenantStageResult.tenantStageChangeOfState()
                        .getTenantDeploymentResourcesToUpdateStatus()
                        .add(dtoToUpdateStatus);

                log.info("Tenant stage \"{}\" deployment resource \"{}\" " +
                                "in tenant \"{}\" marked as created, deploymentId=\"{}\"",
                        tenantStageId,
                        tenantDeploymentResourceId,
                        tenantId,
                        deploymentId);
            });
        }

        return true;
    }
}