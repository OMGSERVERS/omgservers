package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.MatchmakerAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum.CREATED;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateMatchmakerAssignerOperationImpl implements CreateMatchmakerAssignerOperation {

    private static final int MAX_MATCHMAKER_ASSIGNMENTS = 1024;

    @Override
    public MatchmakerAssigner execute(final FetchDeploymentResult fetchDeploymentResult,
                                      final HandleDeploymentResult handleDeploymentResult) {
        final var matchmakerAssigner = new MatchmakerAssigner(MAX_MATCHMAKER_ASSIGNMENTS);

        final var deploymentState = fetchDeploymentResult.deploymentState();
        final var deploymentChangeOfState = handleDeploymentResult.deploymentChangeOfState();

        deploymentState.getDeploymentMatchmakerResources().stream()
                .filter(resource -> resource.getStatus().equals(CREATED))
                .filter(resource -> isNotForUpdate(resource, deploymentChangeOfState))
                .filter(resource -> isNotForDelete(resource, deploymentChangeOfState))
                .forEach(resource -> matchmakerAssigner.addResource(resource.getMatchmakerId(), resource));

        deploymentState.getDeploymentMatchmakerAssignments()
                .forEach(assignment -> matchmakerAssigner.addAssignment(assignment.getMatchmakerId(), assignment));

        return matchmakerAssigner;
    }

    boolean isNotForUpdate(final DeploymentMatchmakerResourceModel deploymentMatchmakerResource,
                           final DeploymentChangeOfStateDto deploymentChangeOfState) {
        final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();
        return deploymentChangeOfState.getDeploymentMatchmakerResourcesToUpdateStatus().stream()
                .map(DeploymentMatchmakerResourceToUpdateStatusDto::id)
                .noneMatch(deploymentMatchmakerResourceId::equals);
    }

    boolean isNotForDelete(final DeploymentMatchmakerResourceModel deploymentMatchmakerResource,
                           final DeploymentChangeOfStateDto deploymentChangeOfState) {
        return !deploymentChangeOfState.getDeploymentLobbyResourcesToDelete()
                .contains(deploymentMatchmakerResource.getId());
    }
}
