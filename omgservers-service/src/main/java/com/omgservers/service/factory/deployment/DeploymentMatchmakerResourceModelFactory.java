package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceConfigDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentMatchmakerResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentMatchmakerResourceModel create(final Long deploymentId,
                                                    final DeploymentMatchmakerResourceConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, matchmakerId, config, idempotencyKey);
    }

    public DeploymentMatchmakerResourceModel create(final Long deploymentId,
                                                    final DeploymentMatchmakerResourceConfigDto config,
                                                    final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var matchmakerId = generateIdOperation.generateId();
        return create(id, deploymentId, matchmakerId, config, idempotencyKey);
    }

    public DeploymentMatchmakerResourceModel create(final Long id,
                                                    final Long deploymentId,
                                                    final Long matchmakerId,
                                                    final DeploymentMatchmakerResourceConfigDto config,
                                                    final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentMatchmakerResource = new DeploymentMatchmakerResourceModel();
        deploymentMatchmakerResource.setId(id);
        deploymentMatchmakerResource.setIdempotencyKey(idempotencyKey);
        deploymentMatchmakerResource.setDeploymentId(deploymentId);
        deploymentMatchmakerResource.setCreated(now);
        deploymentMatchmakerResource.setModified(now);
        deploymentMatchmakerResource.setMatchmakerId(matchmakerId);
        deploymentMatchmakerResource.setStatus(DeploymentMatchmakerResourceStatusEnum.PENDING);
        deploymentMatchmakerResource.setConfig(config);
        deploymentMatchmakerResource.setDeleted(false);
        return deploymentMatchmakerResource;
    }
}
