package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.factory.deployment.DeploymentLobbyAssignmentModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateDeploymentLobbyAssignmentOperationImpl implements CreateDeploymentLobbyAssignmentOperation {

    final DeploymentLobbyAssignmentModelFactory deploymentLobbyAssignmentModelFactory;

    @Override
    public DeploymentLobbyAssignmentModel execute(final DeploymentLobbyResourceModel lobbyResource,
                                                  final DeploymentRequestModel deploymentRequest) {
        final var deploymentId = lobbyResource.getDeploymentId();
        final var lobbyId = lobbyResource.getLobbyId();
        final var clientId = deploymentRequest.getClientId();
        final var idempotencyKey = deploymentRequest.getId().toString();

        final var deploymentLobbyAssignment = deploymentLobbyAssignmentModelFactory.create(deploymentId,
                        clientId,
                        lobbyId,
                        idempotencyKey);

        return deploymentLobbyAssignment;
    }
}
