package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentLobbyAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentLobbyAssignmentModel create(final Long deploymentId,
                                                 final Long clientId,
                                                 final Long lobbyId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, clientId, lobbyId, idempotencyKey);
    }

    public DeploymentLobbyAssignmentModel create(final Long deploymentId,
                                                 final Long clientId,
                                                 final Long lobbyId,
                                                 final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, deploymentId, clientId, lobbyId, idempotencyKey);
    }

    public DeploymentLobbyAssignmentModel create(final Long id,
                                                 final Long deploymentId,
                                                 final Long clientId,
                                                 final Long lobbyId,
                                                 final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentLobbyAssignment = new DeploymentLobbyAssignmentModel();
        deploymentLobbyAssignment.setId(id);
        deploymentLobbyAssignment.setIdempotencyKey(idempotencyKey);
        deploymentLobbyAssignment.setDeploymentId(deploymentId);
        deploymentLobbyAssignment.setCreated(now);
        deploymentLobbyAssignment.setModified(now);
        deploymentLobbyAssignment.setClientId(clientId);
        deploymentLobbyAssignment.setLobbyId(lobbyId);
        deploymentLobbyAssignment.setDeleted(false);
        return deploymentLobbyAssignment;
    }
}
