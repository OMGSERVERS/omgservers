package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
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
class CloseDanglingMatchmakersOperationImpl implements CloseDanglingMatchmakersOperation {

    static private final long MATCHMAKER_MIN_LIFETIME = 300;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        deploymentState.getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource -> deploymentMatchmakerResource.getStatus()
                        .equals(DeploymentMatchmakerResourceStatusEnum.CREATED))
                .forEach(deploymentMatchmakerResource -> {

                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    final var noMatchmakerAssignments = deploymentState.getDeploymentMatchmakerAssignments().stream()
                            .noneMatch(deploymentMatchmakerAssignment ->
                                    deploymentMatchmakerAssignment.getMatchmakerId().equals(matchmakerId));

                    if (noMatchmakerAssignments) {
                        final var matchmakerResourceCreated = deploymentMatchmakerResource.getCreated();
                        final var matchmakerCurrentLifetime = Duration
                                .between(matchmakerResourceCreated, Instant.now());
                        if (matchmakerCurrentLifetime.toSeconds() > MATCHMAKER_MIN_LIFETIME) {
                            final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();

                            final var dtoToUpdateStatus = new DeploymentMatchmakerResourceToUpdateStatusDto(
                                    deploymentMatchmakerResourceId,
                                    DeploymentMatchmakerResourceStatusEnum.CLOSED);

                            handleDeploymentResult.deploymentChangeOfState()
                                    .getDeploymentMatchmakerResourcesToUpdateStatus()
                                    .add(dtoToUpdateStatus);

                            log.info("Matchmaker resource \"{}\" of deployment \"{}\" " +
                                            "is dangling and marked as closed, matchmakerId=\"{}\"",
                                    deploymentMatchmakerResourceId, deploymentId, matchmakerId);
                        }
                    }
                });
    }
}
