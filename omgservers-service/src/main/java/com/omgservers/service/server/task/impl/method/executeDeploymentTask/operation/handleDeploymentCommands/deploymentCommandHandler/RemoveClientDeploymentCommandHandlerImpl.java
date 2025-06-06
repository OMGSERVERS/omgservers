package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.deploymentCommandHandler;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import com.omgservers.schema.model.deploymentCommand.body.RemoveClientDeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation.handleDeploymentCommands.DeploymentCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RemoveClientDeploymentCommandHandlerImpl implements DeploymentCommandHandler {

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.REMOVE_CLIENT;
    }

    @Override
    public boolean handle(final FetchDeploymentResult fetchDeploymentResult,
                          final HandleDeploymentResult handleDeploymentResult,
                          final DeploymentCommandModel deploymentCommand) {
        log.debug("Handle command, {}", deploymentCommand);

        final var body = (RemoveClientDeploymentCommandBodyDto) deploymentCommand.getBody();
        final var clientId = body.getClientId();

        final var deploymentId = fetchDeploymentResult.deploymentId();

        // Remove all client requests

        final var deploymentRequestsToDelete = fetchDeploymentResult.deploymentState().getDeploymentRequests().stream()
                .filter(deploymentRequest -> deploymentRequest.getClientId().equals(clientId))
                .map(DeploymentRequestModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentRequestsToDelete()
                .addAll(deploymentRequestsToDelete);

        // Remove all lobby assignments, if any

        final var deploymentLobbyAssignmentsToDelete = fetchDeploymentResult
                .deploymentState().getDeploymentLobbyAssignments().stream()
                .filter(deploymentLobbyAssignment -> deploymentLobbyAssignment.getClientId().equals(clientId))
                .map(DeploymentLobbyAssignmentModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentLobbyAssignmentToDelete()
                .addAll(deploymentLobbyAssignmentsToDelete);

        // Remove all matchmaker assignments, if any

        final var deploymentMatchmakerAssignments = fetchDeploymentResult
                .deploymentState().getDeploymentMatchmakerAssignments().stream()
                .filter(deploymentMatchmakerAssignment -> deploymentMatchmakerAssignment.getClientId().equals(clientId))
                .toList();

        final var deploymentMatchmakerAssignmentsToDelete = deploymentMatchmakerAssignments.stream()
                .map(DeploymentMatchmakerAssignmentModel::getId)
                .toList();

        handleDeploymentResult.deploymentChangeOfState().getDeploymentMatchmakerAssignmentToDelete()
                .addAll(deploymentMatchmakerAssignmentsToDelete);

        log.info("Client \"{}\" queued to be removed from deployment \"{}\"",
                clientId,
                deploymentId);

        return true;
    }
}
