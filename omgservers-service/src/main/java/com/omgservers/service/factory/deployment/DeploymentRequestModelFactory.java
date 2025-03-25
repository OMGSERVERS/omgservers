package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentRequestModel create(final Long deploymentId,
                                         final Long clientId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, clientId, idempotencyKey);
    }

    public DeploymentRequestModel create(final Long deploymentId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, deploymentId, clientId, idempotencyKey);
    }

    public DeploymentRequestModel create(final Long id,
                                         final Long deploymentId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentRequest = new DeploymentRequestModel();
        deploymentRequest.setId(id);
        deploymentRequest.setIdempotencyKey(idempotencyKey);
        deploymentRequest.setDeploymentId(deploymentId);
        deploymentRequest.setCreated(now);
        deploymentRequest.setModified(now);
        deploymentRequest.setClientId(clientId);
        deploymentRequest.setDeleted(Boolean.FALSE);

        return deploymentRequest;
    }
}
