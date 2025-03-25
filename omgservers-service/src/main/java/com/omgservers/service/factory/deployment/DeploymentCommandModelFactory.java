package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentCommandModel create(final Long deploymentId,
                                         final DeploymentCommandBodyDto body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, body, idempotencyKey);
    }

    public DeploymentCommandModel create(final Long deploymentId,
                                         final DeploymentCommandBodyDto body,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, deploymentId, body, idempotencyKey);
    }

    public DeploymentCommandModel create(final Long id,
                                         final Long deploymentId,
                                         final DeploymentCommandBodyDto body,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentCommand = new DeploymentCommandModel();
        deploymentCommand.setId(id);
        deploymentCommand.setIdempotencyKey(idempotencyKey);
        deploymentCommand.setDeploymentId(deploymentId);
        deploymentCommand.setCreated(now);
        deploymentCommand.setModified(now);
        deploymentCommand.setQualifier(body.getQualifier());
        deploymentCommand.setBody(body);
        deploymentCommand.setDeleted(false);
        return deploymentCommand;
    }
}
