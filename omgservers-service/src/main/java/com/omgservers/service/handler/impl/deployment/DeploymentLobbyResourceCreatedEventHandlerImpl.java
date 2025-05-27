package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceResponse;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.lobby.LobbyShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentLobbyResourceCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final LobbyShard lobbyShard;

    final LobbyModelFactory lobbyModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_LOBBY_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentLobbyResourceCreatedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeploymentLobbyResource(deploymentId, id)
                .flatMap(deploymentLobbyResource -> {
                    log.debug("Created, {}", deploymentLobbyResource);

                    return createLobby(deploymentLobbyResource, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentLobbyResourceModel> getDeploymentLobbyResource(final Long tenantId, final Long id) {
        final var request = new GetDeploymentLobbyResourceRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentLobbyResourceResponse::getDeploymentLobbyResource);
    }

    Uni<Boolean> createLobby(final DeploymentLobbyResourceModel deploymentLobbyResource,
                             final String idempotencyKey) {
        final var lobbyConfig = deploymentLobbyResource.getConfig().getLobbyConfig();

        final var deploymentId = deploymentLobbyResource.getDeploymentId();
        final var lobbyId = deploymentLobbyResource.getLobbyId();
        final var lobby = lobbyModelFactory.create(lobbyId,
                deploymentId,
                lobbyConfig,
                idempotencyKey);
        final var request = new SyncLobbyRequest(lobby);
        return lobbyShard.getService().executeWithIdempotency(request)
                .map(SyncLobbyResponse::getCreated);
    }
}
