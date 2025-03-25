package com.omgservers.service.operation.deployment;

import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import com.omgservers.service.factory.deployment.DeploymentRequestModelFactory;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDeploymentRequestOperationImpl implements CreateDeploymentRequestOperation {

    final DeploymentShard deploymentShard;

    final DeploymentRequestModelFactory deploymentRequestModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final Long clientId,
                                final String idempotencyKey) {
        final var deploymentRequest = deploymentRequestModelFactory.create(deploymentId, clientId, idempotencyKey);
        final var request = new SyncDeploymentRequestRequest(deploymentRequest);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentRequestResponse::getCreated);
    }
}
