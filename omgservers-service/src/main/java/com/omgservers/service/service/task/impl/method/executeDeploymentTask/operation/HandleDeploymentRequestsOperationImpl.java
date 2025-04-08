package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.factory.deployment.DeploymentLobbyResourceModelFactory;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerResourceModelFactory;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleDeploymentRequestsOperationImpl implements HandleDeploymentRequestsOperation {

    final CreateDeploymentMatchmakerAssignmentOperation createDeploymentMatchmakerAssignmentOperation;
    final CreateDeploymentLobbyAssignmentOperation createDeploymentLobbyAssignmentOperation;
    final CreateMatchmakerAssignerOperation createMatchmakerAssignerOperation;
    final CreateLobbyAssignerOperation createLobbyAssignerOperation;

    final DeploymentMatchmakerResourceModelFactory deploymentMatchmakerResourceModelFactory;
    final DeploymentLobbyResourceModelFactory deploymentLobbyResourceModelFactory;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentRequests = fetchDeploymentResult.deploymentState().getDeploymentRequests();
        if (deploymentRequests.isEmpty()) {
            log.trace("Deployment \"{}\" has no requests to process", deploymentId);
        } else {
            final var lobbyAssigner = createLobbyAssignerOperation.execute(fetchDeploymentResult,
                    handleDeploymentResult);

            final var matchmakerAssigner = createMatchmakerAssignerOperation.execute(fetchDeploymentResult,
                    handleDeploymentResult);

            for (final var deploymentRequest : deploymentRequests) {
                final var clientId = deploymentRequest.getClientId();

                final var lobbyNotAssigned = fetchDeploymentResult.deploymentState()
                        .getDeploymentLobbyAssignments().stream()
                        .noneMatch(deploymentLobbyAssignment ->
                                deploymentLobbyAssignment.getClientId().equals(clientId));

                if (lobbyNotAssigned) {
                    final var assignedLobbyOptional = lobbyAssigner.assign(deploymentRequest);

                    if (assignedLobbyOptional.isPresent()) {
                        final var assignedLobby = assignedLobbyOptional.get();
                        final var lobbyAssignment = createDeploymentLobbyAssignmentOperation.execute(assignedLobby,
                                deploymentRequest);
                        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyAssignmentToSync()
                                .add(lobbyAssignment);
                    } else {
                        createLobbyResource(fetchDeploymentResult, handleDeploymentResult);
                    }
                }

                final var matchmakerNotAssigned = fetchDeploymentResult.deploymentState()
                        .getDeploymentMatchmakerAssignments().stream()
                        .noneMatch(deploymentMatchmakerAssignment ->
                                deploymentMatchmakerAssignment.getClientId().equals(clientId));

                if (matchmakerNotAssigned) {
                    final var assignedMatchmakerOptional = matchmakerAssigner.assign(deploymentRequest);

                    if (assignedMatchmakerOptional.isPresent()) {
                        final var assignedMatchmaker = assignedMatchmakerOptional.get();
                        final var matchmakerAssignment = createDeploymentMatchmakerAssignmentOperation
                                .execute(assignedMatchmaker, deploymentRequest);
                        handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerAssignmentToSync()
                                .add(matchmakerAssignment);
                    } else {
                        createMatchmakerResource(fetchDeploymentResult, handleDeploymentResult);
                    }
                }
            }
        }
    }

    // TODO: Check deployment limits

    void createLobbyResource(final FetchDeploymentResult fetchDeploymentResult,
                             final HandleDeploymentResult handleDeploymentResult) {
        if (handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToSync().isEmpty()) {

            final var deploymentId = fetchDeploymentResult.deploymentId();

            final var noPendingLobbies = fetchDeploymentResult.deploymentState()
                    .getDeploymentLobbyResources().stream()
                    .map(DeploymentLobbyResourceModel::getStatus)
                    .noneMatch(DeploymentLobbyResourceStatusEnum.PENDING::equals);

            if (noPendingLobbies) {
                final var deploymentLobbyResource = deploymentLobbyResourceModelFactory.create(deploymentId);
                handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyResourcesToSync()
                        .add(deploymentLobbyResource);
            }
        }
    }

    void createMatchmakerResource(final FetchDeploymentResult fetchDeploymentResult,
                                  final HandleDeploymentResult handleDeploymentResult) {
        if (handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToSync().isEmpty()) {

            final var deploymentId = fetchDeploymentResult.deploymentId();

            final var noPendingMatchmakers = fetchDeploymentResult.deploymentState()
                    .getDeploymentMatchmakerResources().stream()
                    .map(DeploymentMatchmakerResourceModel::getStatus)
                    .noneMatch(DeploymentMatchmakerResourceStatusEnum.PENDING::equals);

            if (noPendingMatchmakers) {
                final var deploymentMatchmakerResource = deploymentMatchmakerResourceModelFactory.create(deploymentId);
                handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerResourcesToSync()
                        .add(deploymentMatchmakerResource);
            }
        }
    }
}
