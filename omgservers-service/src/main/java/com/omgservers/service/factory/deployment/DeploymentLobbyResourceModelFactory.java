package com.omgservers.service.factory.deployment;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentLobbyResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public DeploymentLobbyResourceModel create(final Long deploymentId,
                                               final DeploymentLobbyResourceConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, deploymentId, lobbyId, config, idempotencyKey);
    }

    public DeploymentLobbyResourceModel create(final Long deploymentId,
                                               final DeploymentLobbyResourceConfigDto config,
                                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var lobbyId = generateIdOperation.generateId();
        return create(id, deploymentId, lobbyId, config, idempotencyKey);
    }

    public DeploymentLobbyResourceModel create(final Long id,
                                               final Long deploymentId,
                                               final Long lobbyId,
                                               final DeploymentLobbyResourceConfigDto config,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var deploymentLobbyResource = new DeploymentLobbyResourceModel();
        deploymentLobbyResource.setId(id);
        deploymentLobbyResource.setIdempotencyKey(idempotencyKey);
        deploymentLobbyResource.setDeploymentId(deploymentId);
        deploymentLobbyResource.setCreated(now);
        deploymentLobbyResource.setModified(now);
        deploymentLobbyResource.setLobbyId(lobbyId);
        deploymentLobbyResource.setStatus(DeploymentLobbyResourceStatusEnum.PENDING);
        deploymentLobbyResource.setConfig(config);
        deploymentLobbyResource.setDeleted(false);
        return deploymentLobbyResource;
    }
}
