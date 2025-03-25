package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentMatchmakerAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentMatchmakerAssignmentModel create(final Long deploymentId,
                                                      final Long clientId,
                                                      final Long matchmakerId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, clientId, matchmakerId, idempotencyKey);
    }

    public DeploymentMatchmakerAssignmentModel create(final Long deploymentId,
                                                      final Long clientId,
                                                      final Long matchmakerId,
                                                      final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, deploymentId, clientId, matchmakerId, idempotencyKey);
    }

    public DeploymentMatchmakerAssignmentModel create(final Long id,
                                                      final Long deploymentId,
                                                      final Long clientId,
                                                      final Long matchmakerId,
                                                      final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentMatchmakerAssignment = new DeploymentMatchmakerAssignmentModel();
        deploymentMatchmakerAssignment.setId(id);
        deploymentMatchmakerAssignment.setIdempotencyKey(idempotencyKey);
        deploymentMatchmakerAssignment.setDeploymentId(deploymentId);
        deploymentMatchmakerAssignment.setCreated(now);
        deploymentMatchmakerAssignment.setModified(now);
        deploymentMatchmakerAssignment.setClientId(clientId);
        deploymentMatchmakerAssignment.setMatchmakerId(matchmakerId);
        deploymentMatchmakerAssignment.setDeleted(false);
        return deploymentMatchmakerAssignment;
    }
}
