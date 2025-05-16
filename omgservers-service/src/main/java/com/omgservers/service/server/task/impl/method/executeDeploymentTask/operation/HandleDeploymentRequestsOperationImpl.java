package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
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

    final CreateMatchmakerResourceOperation createMatchmakerResourceOperation;
    final CreateLobbyResourceOperation createLobbyResourceOperation;

    @Override
    public void execute(final FetchDeploymentResult fetchDeploymentResult,
                        final HandleDeploymentResult handleDeploymentResult) {
        final var deploymentId = fetchDeploymentResult.deploymentId();

        final var deploymentRequests = fetchDeploymentResult.deploymentState().getDeploymentRequests();
        if (deploymentRequests.isEmpty()) {
            log.debug("Deployment \"{}\" has no requests to process", deploymentId);
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
                        createLobbyResourceOperation.execute(fetchDeploymentResult, handleDeploymentResult);
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
                        createMatchmakerResourceOperation.execute(fetchDeploymentResult, handleDeploymentResult);
                    }
                }

                // Queue the request for deletion if the client is fully assigned
                if (!matchmakerNotAssigned && !lobbyNotAssigned) {
                    handleDeploymentResult.deploymentChangeOfState().getDeploymentRequestsToDelete()
                            .add(deploymentRequest.getId());
                }
            }
        }
    }
}
