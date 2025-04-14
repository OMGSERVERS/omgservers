package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.deploymentCommand.body.RemoveClientDeploymentCommandBodyDto;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientDeletedEventBodyModel;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
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
public class ClientDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final ClientShard clientShard;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (ClientDeletedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getClient(clientId)
                .flatMap(client -> {
                    log.debug("Deleted, {}", client);

                    final var deploymentId = client.getDeploymentId();
                    return createRemoveClientDeploymentCommand(deploymentId, clientId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> createRemoveClientDeploymentCommand(final Long deploymentId,
                                                     final Long clientId,
                                                     final String idempotencyKey) {
        final var commandBody = new RemoveClientDeploymentCommandBodyDto(clientId);
        final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId, commandBody, idempotencyKey);

        final var request = new SyncDeploymentCommandRequest(deploymentCommand);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentCommandResponse::getCreated);
    }
}

