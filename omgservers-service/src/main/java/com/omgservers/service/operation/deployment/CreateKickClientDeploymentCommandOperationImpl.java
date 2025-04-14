package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.deploymentCommand.body.KickClientDeploymentCommandBodyDto;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
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
class CreateKickClientDeploymentCommandOperationImpl implements CreateKickClientDeploymentCommandOperation {

    final DeploymentShard deploymentShard;
    final ClientShard clientShard;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final String idempotencyKey) {
        return getClient(clientId)
                .flatMap(client -> {
                    final var deploymentId = client.getDeploymentId();

                    final var commandBody = new KickClientDeploymentCommandBodyDto(clientId);
                    final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId,
                            commandBody,
                            idempotencyKey);

                    final var request = new SyncDeploymentCommandRequest(deploymentCommand);
                    return deploymentShard.getService().executeWithIdempotency(request)
                            .map(SyncDeploymentCommandResponse::getCreated);
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }
}
