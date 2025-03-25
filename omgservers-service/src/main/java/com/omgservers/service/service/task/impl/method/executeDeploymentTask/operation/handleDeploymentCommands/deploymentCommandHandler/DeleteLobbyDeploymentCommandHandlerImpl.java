package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.DeleteLobbyDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
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
class DeleteLobbyDeploymentCommandHandlerImpl implements DeploymentCommandHandler {

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.DELETE_LOBBY;
    }

    @Override
    public boolean handle(final FetchDeploymentResult fetchDeploymentResult,
                          final HandleDeploymentResult handleDeploymentResult,
                          final DeploymentCommandModel deploymentCommand) {
        log.trace("Handle command, {}", deploymentCommand);

        final var body = (DeleteLobbyDeploymentCommandBodyDto) deploymentCommand.getBody();
        final var lobbyId = body.getLobbyId();
        final var reason = body.getReason();

        final var deploymentId = fetchDeploymentResult.deploymentId();

        fetchDeploymentResult.deploymentState().getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getLobbyId().equals(lobbyId))
                .map(DeploymentLobbyResourceModel::getId)
                .forEach(deploymentLobbyResourceId -> {
                    handleDeploymentResult.deploymentChangeOfState()
                            .getDeploymentLobbyResourcesToDelete()
                            .add(deploymentLobbyResourceId);

                    log.info("Lobby resource \"{}\" of deployment \"{}\" " +
                                    "marked to be deleted with reason={}, lobbyId={}",
                            deploymentLobbyResourceId,
                            deploymentId,
                            reason,
                            lobbyId);
                });

        return true;
    }
}
