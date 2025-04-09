package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.OpenLobbyDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.DeploymentCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenLobbyDeploymentCommandHandlerImpl implements DeploymentCommandHandler {

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.OPEN_LOBBY;
    }

    @Override
    public boolean handle(final FetchDeploymentResult fetchDeploymentResult,
                          final HandleDeploymentResult handleDeploymentResult,
                          final DeploymentCommandModel deploymentCommand) {
        log.trace("Handle command, {}", deploymentCommand);

        final var body = (OpenLobbyDeploymentCommandBodyDto) deploymentCommand.getBody();
        final var lobbyId = body.getLobbyId();

        final var deploymentId = fetchDeploymentResult.deploymentId();

        fetchDeploymentResult.deploymentState().getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getLobbyId().equals(lobbyId))
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getStatus()
                        .equals(DeploymentLobbyResourceStatusEnum.PENDING))
                .map(DeploymentLobbyResourceModel::getId)
                .forEach(deploymentLobbyResourceId -> {
                    final var dtoToUpdateStatus = new DeploymentLobbyResourceToUpdateStatusDto(
                            deploymentLobbyResourceId,
                            DeploymentLobbyResourceStatusEnum.CREATED);

                    handleDeploymentResult.deploymentChangeOfState()
                            .getDeploymentLobbyResourcesToUpdateStatus()
                            .add(dtoToUpdateStatus);

                    log.info("Lobby resource \"{}\" from deployment \"{}\" is opened and marked as created, lobbyId={}",
                            deploymentLobbyResourceId,
                            deploymentId,
                            lobbyId);
                });

        return true;
    }
}
