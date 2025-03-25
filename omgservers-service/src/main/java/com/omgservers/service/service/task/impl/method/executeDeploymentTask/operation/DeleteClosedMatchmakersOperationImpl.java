package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClosedMatchmakersOperationImpl implements DeleteClosedMatchmakersOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentState = fetchDeploymentResult.deploymentState();

        deploymentState.getDeploymentMatchmakerResources().stream()
                .filter(deploymentMatchmakerResource -> deploymentMatchmakerResource.getStatus()
                        .equals(DeploymentMatchmakerResourceStatusEnum.CLOSED))
                .forEach(deploymentMatchmakerResource -> {

                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    final var noMatchmakerAssignments = deploymentState.getDeploymentMatchmakerAssignments().stream()
                            .noneMatch(deploymentMatchmakerAssignment ->
                                    deploymentMatchmakerAssignment.getMatchmakerId().equals(matchmakerId));

                    if (noMatchmakerAssignments) {
                        final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();

                        handleDeploymentResult.deploymentChangeOfState()
                                .getDeploymentMatchmakerResourcesToDelete()
                                .add(deploymentMatchmakerResourceId);

                        log.info("Matchmaker resource \"{}\" of deployment \"{}\" " +
                                        "is closed and marked to be deleted, matchmakerId=\"{}\"",
                                deploymentMatchmakerResourceId, deploymentId, matchmakerId);
                    }
                });
    }
}
