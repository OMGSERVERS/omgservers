package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClosedLobbiesOperationImpl implements DeleteClosedLobbiesOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        deploymentState.getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getStatus()
                        .equals(DeploymentLobbyResourceStatusEnum.CLOSED))
                .forEach(deploymentLobbyResource -> {

                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    final var noLobbyAssignments = deploymentState.getDeploymentLobbyAssignments().stream()
                            .noneMatch(deploymentLobbyAssignment ->
                                    deploymentLobbyAssignment.getLobbyId().equals(lobbyId));

                    if (noLobbyAssignments) {
                        final var deploymentLobbyResourceId = deploymentLobbyResource.getId();

                        handleDeploymentResult.deploymentChangeOfState()
                                .getDeploymentLobbyResourcesToDelete()
                                .add(deploymentLobbyResourceId);

                        log.info("Lobby resource \"{}\" of deployment \"{}\" is closed and marked to be deleted, " +
                                "lobbyId=\"{}\"", deploymentLobbyResourceId, deploymentId, lobbyId);
                    }
                });
    }
}
