package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceConfigDto;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceResponse;
import com.omgservers.service.factory.deployment.DeploymentMatchmakerResourceModelFactory;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDeploymentMatchmakerResourceOperationImpl implements CreateDeploymentMatchmakerResourceOperation {

    final DeploymentShard deploymentShard;

    final DeploymentMatchmakerResourceModelFactory deploymentMatchmakerResourceModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final String idempotencyKey) {
        final var deploymentMatchmakerResourceConfigDto = new DeploymentMatchmakerResourceConfigDto();
        final var deploymentMatchmakerResource = deploymentMatchmakerResourceModelFactory
                .create(deploymentId, deploymentMatchmakerResourceConfigDto, idempotencyKey);
        final var request = new SyncDeploymentMatchmakerResourceRequest(deploymentMatchmakerResource);
        return deploymentShard.getService().execute(request)
                .map(SyncDeploymentMatchmakerResourceResponse::getCreated);
    }
}
