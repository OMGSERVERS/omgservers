package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.runtimeCommand.body.RemoveClientRuntimeCommandBodyDto;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentResponse;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentLobbyAssignmentDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final RuntimeShard runtimeShard;
    final LobbyShard lobbyShard;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_LOBBY_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (DeploymentLobbyAssignmentDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeploymentLobbyAssignment(deploymentId, id)
                .flatMap(deploymentLobbyAssignment -> {
                    log.debug("Deleted, {}", deploymentLobbyAssignment);

                    final var lobbyId = deploymentLobbyAssignment.getLobbyId();
                    return getLobby(lobbyId)
                            .flatMap(lobby -> {
                                final var runtimeId = lobby.getRuntimeId();
                                final var clientId = deploymentLobbyAssignment.getClientId();
                                return createRemoveClientRuntimeCommand(runtimeId, clientId, idempotencyKey);
                            });
                })
                .replaceWithVoid();
    }

    Uni<DeploymentLobbyAssignmentModel> getDeploymentLobbyAssignment(final Long tenantId, final Long id) {
        final var request = new GetDeploymentLobbyAssignmentRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentLobbyAssignmentResponse::getDeploymentLobbyAssignment);
    }

    Uni<LobbyModel> getLobby(final Long id) {
        final var request = new GetLobbyRequest(id);
        return lobbyShard.getService().execute(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Boolean> createRemoveClientRuntimeCommand(final Long runtimeId,
                                                  final Long clientId,
                                                  final String idempotencyKey) {
        final var commandBody = new RemoveClientRuntimeCommandBodyDto(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, commandBody, idempotencyKey);

        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }
}
