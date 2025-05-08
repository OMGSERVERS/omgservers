package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.DeleteLobbyDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
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

        final var matchedLobbyResourcesToClose = fetchDeploymentResult
                .deploymentState().getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getLobbyId().equals(lobbyId))
                // Handle the lobby resource regardless of its status
                .toList();

        if (matchedLobbyResourcesToClose.isEmpty()) {
            log.warn("No lobby resource found to close for lobbyId=\"{}\" in deployment=\"{}\", skip command",
                    lobbyId, deploymentId);
        } else {
            matchedLobbyResourcesToClose.stream()
                    .map(DeploymentLobbyResourceModel::getId)
                    .forEach(deploymentLobbyResourceId -> {
                        final var dtoToUpdateStatus = new DeploymentLobbyResourceToUpdateStatusDto(
                                deploymentLobbyResourceId,
                                DeploymentLobbyResourceStatusEnum.CLOSED);

                        handleDeploymentResult.deploymentChangeOfState()
                                .getDeploymentLobbyResourcesToUpdateStatus()
                                .add(dtoToUpdateStatus);

                        log.info("Lobby resource \"{}\" from deployment \"{}\" " +
                                        "must be deleted due to \"{}\" and marked as closed, lobbyId={}",
                                deploymentLobbyResourceId,
                                deploymentId,
                                reason,
                                lobbyId);

                        final var deploymentLobbyAssignmentToDelete = fetchDeploymentResult
                                .deploymentState().getDeploymentLobbyAssignments().stream()
                                .filter(deploymentLobbyAssignment -> deploymentLobbyAssignment.getLobbyId()
                                        .equals(lobbyId))
                                .map(DeploymentLobbyAssignmentModel::getId)
                                .toList();

                        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyAssignmentToDelete()
                                .addAll(deploymentLobbyAssignmentToDelete);
                    });
        }

        return true;
    }
}
