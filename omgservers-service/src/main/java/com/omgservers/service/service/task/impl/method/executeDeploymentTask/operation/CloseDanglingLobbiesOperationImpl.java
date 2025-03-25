package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
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

        deploymentState.getDeploymentLobbyResources().stream()
                .filter(deploymentLobbyResource -> deploymentLobbyResource.getStatus()
                        .equals(DeploymentLobbyResourceStatusEnum.CREATED))
                .forEach(deploymentLobbyResource -> {

                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    final var noLobbyAssignments = deploymentState.getDeploymentLobbyAssignments().stream()
                            .noneMatch(deploymentLobbyAssignment ->
                                    deploymentLobbyAssignment.getLobbyId().equals(lobbyId));

                    if (noLobbyAssignments) {
                        final var lobbyResourceCreated = deploymentLobbyResource.getCreated();
                        final var lobbyCurrentLifetime = Duration.between(lobbyResourceCreated, Instant.now());
                        final var lobbyMinLifetime = getServiceConfigOperation.getServiceConfig().runtimes()
                                .lobby().minLifetime();
                        if (lobbyCurrentLifetime.toSeconds() > lobbyMinLifetime) {
                            final var deploymentLobbyResourceId = deploymentLobbyResource.getId();

                            final var dtoToUpdateStatus = new DeploymentLobbyResourceToUpdateStatusDto(
                                    deploymentLobbyResourceId,
                                    DeploymentLobbyResourceStatusEnum.CLOSED);

                            handleDeploymentResult.deploymentChangeOfState()
                                    .getDeploymentLobbyResourcesToUpdateStatus()
                                    .add(dtoToUpdateStatus);

                            log.info("Lobby resource \"{}\" of deployment \"{}\" is dangling and marked as closed, " +
                                    "lobbyId=\"{}\"", deploymentLobbyResourceId, deploymentId, lobbyId);
                        }
                    }
                });
    }
}
