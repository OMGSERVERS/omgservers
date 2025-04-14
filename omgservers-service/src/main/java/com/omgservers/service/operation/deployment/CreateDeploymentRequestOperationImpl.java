package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import com.omgservers.service.factory.deployment.DeploymentRequestModelFactory;
import com.omgservers.service.shard.client.ClientShard;
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
    final ClientShard clientShard;

    final DeploymentRequestModelFactory deploymentRequestModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final String idempotencyKey) {
        return getClient(clientId)
                .flatMap(client -> {
                    if (client.getDeleted()) {
                        log.info("Client \"{}\" is deleted, skip operation", clientId);
                        return Uni.createFrom().item(Boolean.FALSE);
                    }

                    final var deploymentId = client.getDeploymentId();
                    return createDeploymentRequest(deploymentId, clientId, idempotencyKey);
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> createDeploymentRequest(final Long deploymentId,
                                         final Long clientId,
                                         final String idempotencyKey) {
        final var deploymentRequest = deploymentRequestModelFactory.create(deploymentId,
                clientId,
                idempotencyKey);

        final var request = new SyncDeploymentRequestRequest(deploymentRequest);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentRequestResponse::getCreated);
    }
}
