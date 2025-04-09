package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClosedLobbiesOperationImpl implements DeleteClosedLobbiesOperation {

    static private final long GRACEFUL_INTERVAL = 16;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentLobbyResourcesToDelete = deploymentState.getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getStatus()
                        .equals(DeploymentLobbyResourceStatusEnum.CLOSED))
                .filter(deploymentLobbyResource -> {
                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    return deploymentState.getDeploymentLobbyAssignments().stream()
                            .noneMatch(deploymentLobbyAssignment -> deploymentLobbyAssignment.getLobbyId()
                                    .equals(lobbyId));
                })
                .filter(deploymentLobbyResource -> {
                    final var lobbyResourceModified = deploymentLobbyResource.getModified();
                    final var lobbyCurrentInterval = Duration.between(lobbyResourceModified, Instant.now());
                    return lobbyCurrentInterval.toSeconds() > GRACEFUL_INTERVAL;
                })
                .peek(deploymentLobbyResource -> {
                    final var deploymentLobbyResourceId = deploymentLobbyResource.getId();
                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    log.info("Lobby resource \"{}\" from deployment \"{}\" " +
                                    "is closed and queued for deletion, lobbyId=\"{}\"",
                            deploymentLobbyResourceId,
                            deploymentId,
                            lobbyId);
                })
                .map(DeploymentLobbyResourceModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToDelete()
                .addAll(deploymentLobbyResourcesToDelete);
    }
}
