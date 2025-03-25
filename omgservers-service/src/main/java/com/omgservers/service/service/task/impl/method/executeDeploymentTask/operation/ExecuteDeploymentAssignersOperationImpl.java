package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.LobbyAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.MatchmakerAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.ExecuteDeploymentAssignersResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteDeploymentAssignersOperationImpl implements ExecuteDeploymentAssignersOperation {

    final CreateDeploymentMatchmakerAssignmentOperation createDeploymentMatchmakerAssignmentOperation;
    final CreateDeploymentLobbyAssignmentOperation createDeploymentLobbyAssignmentOperation;
    final CreateMatchmakerAssignerOperation createMatchmakerAssignerOperation;
    final CreateLobbyAssignerOperation createLobbyAssignerOperation;

    @Override
    public ExecuteDeploymentAssignersResult execute(final List<DeploymentRequestModel> deploymentRequests,
                                                    final LobbyAssigner lobbyAssigner,
                                                    final MatchmakerAssigner matchmakerAssigner,
                                                    final HandleDeploymentResult handleDeploymentResult) {

        for (final var deploymentRequest : deploymentRequests) {
            final var assignedLobbyOptional = lobbyAssigner.assign(deploymentRequest);
            final var assignedMatchmakerOptional = matchmakerAssigner.assign(deploymentRequest);

            final var lobbyNotAssigned = assignedLobbyOptional.isEmpty();
            final var matchmakerNotAssigned = assignedMatchmakerOptional.isEmpty();

            if (lobbyNotAssigned || matchmakerNotAssigned) {
                return new ExecuteDeploymentAssignersResult(lobbyNotAssigned, matchmakerNotAssigned);
            } else {
                final var assignedLobby = assignedLobbyOptional.get();
                final var lobbyAssignment = createDeploymentLobbyAssignmentOperation
                        .execute(assignedLobby, deploymentRequest);
                handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyAssignmentToSync()
                        .add(lobbyAssignment);

                final var assignedMatchmaker = assignedMatchmakerOptional.get();
                final var matchmakerAssignment = createDeploymentMatchmakerAssignmentOperation
                        .execute(assignedMatchmaker, deploymentRequest);
                handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerAssignmentToSync()
                        .add(matchmakerAssignment);

                handleDeploymentResult.deploymentChangeOfState().getDeploymentRequestsToDelete()
                        .add(deploymentRequest.getId());
            }
        }

        return new ExecuteDeploymentAssignersResult(false, false);
    }
}
