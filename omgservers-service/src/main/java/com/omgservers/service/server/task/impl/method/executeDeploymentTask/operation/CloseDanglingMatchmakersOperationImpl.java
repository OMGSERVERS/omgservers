package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
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
class CloseDanglingMatchmakersOperationImpl implements CloseDanglingMatchmakersOperation {

    static private final long MATCHMAKER_MIN_LIFETIME = 600;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentMatchmakerResourcesToUpdateStatus = deploymentState
                .getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource ->
                        deploymentMatchmakerResource.getStatus().equals(DeploymentMatchmakerResourceStatusEnum.CREATED))
                .filter(deploymentMatchmakerResource -> {
                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    return deploymentState.getDeploymentMatchmakerAssignments().stream()
                            .noneMatch(deploymentMatchmakerAssignment ->
                                    deploymentMatchmakerAssignment.getMatchmakerId().equals(matchmakerId));
                })
                .filter(deploymentMatchmakerResource -> {
                    final var matchmakerResourceCreated = deploymentMatchmakerResource.getCreated();
                    final var matchmakerCurrentLifetime = Duration
                            .between(matchmakerResourceCreated, Instant.now());
                    return matchmakerCurrentLifetime.toSeconds() > MATCHMAKER_MIN_LIFETIME;
                })
                .map(deploymentMatchmakerResource -> {
                    final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();
                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();

                    log.info("Matchmaker resource \"{}\" of deployment \"{}\" " +
                                    "is dangling and marked as closed, matchmakerId=\"{}\"",
                            deploymentMatchmakerResourceId, deploymentId, matchmakerId);

                    final var dtoToUpdateStatus = new DeploymentMatchmakerResourceToUpdateStatusDto(
                            deploymentMatchmakerResourceId,
                            DeploymentMatchmakerResourceStatusEnum.CLOSED);

                    return dtoToUpdateStatus;
                })
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToUpdateStatus()
                .addAll(deploymentMatchmakerResourcesToUpdateStatus);
    }
}
