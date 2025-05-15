package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceResponse;
import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceDeletedEventBodyModel;
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
public class DeploymentLobbyResourceDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final LobbyShard lobbyShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_LOBBY_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentLobbyResourceDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        return getDeploymentLobbyResource(deploymentId, id)
                .flatMap(deploymentLobbyResource -> {
                    log.debug("Deleted, {}", deploymentLobbyResource);

                    final var lobbyId = deploymentLobbyResource.getLobbyId();
                    return deleteLobby(lobbyId);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentLobbyResourceModel> getDeploymentLobbyResource(final Long deploymentId, final Long id) {
        final var request = new GetDeploymentLobbyResourceRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentLobbyResourceResponse::getDeploymentLobbyResource);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyShard.getService().execute(request)
                .map(DeleteLobbyResponse::getDeleted);
    }
}
