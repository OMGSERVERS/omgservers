package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.LobbyAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum.CREATED;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateLobbyAssignerOperationImpl implements CreateLobbyAssignerOperation {

    private static final int MAX_LOBBY_ASSIGNMENTS = 128;

    @Override
    public LobbyAssigner execute(final FetchDeploymentResult fetchDeploymentResult,
                                 final HandleDeploymentResult handleDeploymentResult) {
        final var lobbyAssigner = new LobbyAssigner(MAX_LOBBY_ASSIGNMENTS);

        final var deploymentState = fetchDeploymentResult.deploymentState();
        final var deploymentChangeOfState = handleDeploymentResult.deploymentChangeOfState();

        deploymentState.getDeploymentLobbyResources().stream()
                .filter(resource -> resource.getStatus().equals(CREATED))
                .filter(resource -> isNotForUpdate(resource, deploymentChangeOfState))
                .filter(resource -> isNotForDelete(resource, deploymentChangeOfState))
                .forEach(deploymentLobbyResource -> lobbyAssigner.addResource(deploymentLobbyResource.getLobbyId(),
                        deploymentLobbyResource));

        deploymentState.getDeploymentLobbyAssignments()
                .forEach(assignment -> lobbyAssigner.addAssignment(assignment.getLobbyId(), assignment));

        return lobbyAssigner;
    }

    boolean isNotForUpdate(final DeploymentLobbyResourceModel deploymentLobbyResource,
                           final DeploymentChangeOfStateDto deploymentChangeOfState) {
        final var deploymentLobbyResourceId = deploymentLobbyResource.getId();
        return deploymentChangeOfState.getDeploymentLobbyResourcesToUpdateStatus().stream()
                .map(DeploymentLobbyResourceToUpdateStatusDto::id)
                .noneMatch(deploymentLobbyResourceId::equals);
    }

    boolean isNotForDelete(final DeploymentLobbyResourceModel deploymentLobbyResource,
                           final DeploymentChangeOfStateDto deploymentChangeOfState) {
        return !deploymentChangeOfState.getDeploymentLobbyResourcesToDelete()
                .contains(deploymentLobbyResource.getId());
    }
}
