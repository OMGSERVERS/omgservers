package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
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
class CloseDanglingLobbiesOperationImpl implements CloseDanglingLobbiesOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentLobbyResourcesToUpdateStatus = deploymentState.getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getStatus()
                        .equals(DeploymentLobbyResourceStatusEnum.CREATED))
                .filter(deploymentLobbyResource -> {
                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    return deploymentState.getDeploymentLobbyAssignments().stream()
                            .noneMatch(deploymentLobbyAssignment -> deploymentLobbyAssignment.getLobbyId()
                                    .equals(lobbyId));
                })
                .filter(deploymentLobbyResource -> {
                    final var lobbyResourceCreated = deploymentLobbyResource.getCreated();
                    final var lobbyCurrentLifetime = Duration.between(lobbyResourceCreated, Instant.now());
                    final var lobbyMinLifetime = getServiceConfigOperation.getServiceConfig().runtimes()
                            .lobby().minLifetime();
                    return lobbyCurrentLifetime.toSeconds() > lobbyMinLifetime;
                })
                .map(deploymentLobbyResource -> {
                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    final var deploymentLobbyResourceId = deploymentLobbyResource.getId();

                    log.info("Lobby resource \"{}\" from deployment \"{}\" is dangling " +
                                    "and marked as closed, lobbyId=\"{}\"",
                            deploymentLobbyResourceId,
                            deploymentId,
                            lobbyId);

                    final var dtoToUpdateStatus = new DeploymentLobbyResourceToUpdateStatusDto(
                            deploymentLobbyResourceId,
                            DeploymentLobbyResourceStatusEnum.CLOSED);

                    return dtoToUpdateStatus;
                })
                .toList();


        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToUpdateStatus()
                .addAll(deploymentLobbyResourcesToUpdateStatus);
    }
}
