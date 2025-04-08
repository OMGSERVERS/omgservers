package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.OpenMatchmakerDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.DeploymentCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenMatchmakerDeploymentCommandHandlerImpl implements DeploymentCommandHandler {

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.OPEN_MATCHMAKER;
    }

    @Override
    public boolean handle(final FetchDeploymentResult fetchDeploymentResult,
                          final HandleDeploymentResult handleDeploymentResult,
                          final DeploymentCommandModel deploymentCommand) {
        log.trace("Handle command, {}", deploymentCommand);

        final var body = (OpenMatchmakerDeploymentCommandBodyDto) deploymentCommand.getBody();
        final var matchmakerId = body.getMatchmakerId();

        final var deploymentId = fetchDeploymentResult.deploymentId();

        fetchDeploymentResult.deploymentState().getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource -> deploymentMatchmakerResource.getMatchmakerId()
                        .equals(matchmakerId))
                .filter(deploymentMatchmakerResource -> deploymentMatchmakerResource.getStatus()
                        .equals(DeploymentMatchmakerResourceStatusEnum.PENDING))
                .map(DeploymentMatchmakerResourceModel::getId)
                .forEach(deploymentMatchmakerResourceId -> {
                    final var dtoToUpdateStatus = new DeploymentMatchmakerResourceToUpdateStatusDto(
                            deploymentMatchmakerResourceId,
                            DeploymentMatchmakerResourceStatusEnum.CREATED);

                    handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToUpdateStatus()
                            .add(dtoToUpdateStatus);

                    log.info("Matchmaker resource \"{}\" of deployment \"{}\" is opened and marked as created",
                            deploymentMatchmakerResourceId,
                            deploymentId);
                });

        return true;
    }
}
