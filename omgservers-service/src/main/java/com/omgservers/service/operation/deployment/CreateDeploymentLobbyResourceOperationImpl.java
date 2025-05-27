package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceResponse;
import com.omgservers.service.factory.deployment.DeploymentLobbyResourceModelFactory;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDeploymentLobbyResourceOperationImpl implements CreateDeploymentLobbyResourceOperation {

    final DeploymentShard deploymentShard;

    final DeploymentLobbyResourceModelFactory deploymentLobbyResourceModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final String idempotencyKey) {
        final var deploymentLobbyResourceConfigDto = new DeploymentLobbyResourceConfigDto();
        final var deploymentLobbyResource = deploymentLobbyResourceModelFactory
                .create(deploymentId, deploymentLobbyResourceConfigDto, idempotencyKey);
        final var request = new SyncDeploymentLobbyResourceRequest(deploymentLobbyResource);
        return deploymentShard.getService().execute(request)
                .map(SyncDeploymentLobbyResourceResponse::getCreated);
    }
}
