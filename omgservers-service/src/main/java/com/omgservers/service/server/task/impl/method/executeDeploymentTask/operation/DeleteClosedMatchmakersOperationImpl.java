package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
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
class DeleteClosedMatchmakersOperationImpl implements DeleteClosedMatchmakersOperation {

    static private final long GRACEFUL_INTERVAL = 16;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        final var deploymentMatchmakerResourcesToDelete = deploymentState.getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource -> deploymentMatchmakerResource.getStatus()
                        .equals(DeploymentMatchmakerResourceStatusEnum.CLOSED))
                .filter(deploymentMatchmakerResource -> {
                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    return deploymentState.getDeploymentMatchmakerAssignments().stream()
                            .noneMatch(deploymentMatchmakerAssignment ->
                                    deploymentMatchmakerAssignment.getMatchmakerId().equals(matchmakerId));
                })
                .filter(deploymentMatchmakerResource -> {
                    final var matchmakerResourceModified = deploymentMatchmakerResource.getModified();
                    final var matchmakerCurrentInterval = Duration.between(matchmakerResourceModified, Instant.now());
                    return matchmakerCurrentInterval.toSeconds() > GRACEFUL_INTERVAL;
                })
                .peek(deploymentMatchmakerResource -> {
                    final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();
                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    log.info("Matchmaker resource \"{}\" of deployment \"{}\" " +
                                    "is closed and queued for deletion, matchmakerId=\"{}\"",
                            deploymentMatchmakerResourceId, deploymentId, matchmakerId);
                })
                .map(DeploymentMatchmakerResourceModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToDelete()
                .addAll(deploymentMatchmakerResourcesToDelete);
    }
}
