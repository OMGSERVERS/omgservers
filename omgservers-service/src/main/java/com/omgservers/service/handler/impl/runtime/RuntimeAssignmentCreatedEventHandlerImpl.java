package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.body.AddClientRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyDto;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.SyncClientRuntimeRefResponse;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeAssignmentCreatedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final ClientShard clientShard;
    final UserShard userShard;

    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeAssignmentCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return getRuntimeAssignment(runtimeId, id)
                .flatMap(runtimeAssignment -> {
                    log.debug("Created, {}", runtimeAssignment);

                    final var clientId = runtimeAssignment.getClientId();
                    final var idempotencyKey = event.getId().toString();

                    return syncAddClientRuntimeCommand(runtimeAssignment, idempotencyKey)
                            .flatMap(created -> syncClientRuntimeRef(clientId,
                                    runtimeId,
                                    idempotencyKey));
                })
                .replaceWithVoid();
    }


    Uni<RuntimeAssignmentModel> getRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new GetRuntimeAssignmentRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final RuntimeAssignmentModel runtimeAssignment,
                                             final String idempotencyKey) {
        final var clientId = runtimeAssignment.getClientId();

        return getClient(clientId)
                .flatMap(client -> {
                    final var userId = client.getUserId();
                    final var playerId = client.getPlayerId();

                    return getPlayer(userId, playerId)
                            .flatMap(player -> {
                                final var profile = player.getProfile();

                                final var runtimeId = runtimeAssignment.getRuntimeId();

                                if (Objects.nonNull(runtimeAssignment.getConfig().getMatchmakerMatchAssignment())) {
                                    final var groupName = runtimeAssignment.getConfig().getMatchmakerMatchAssignment().getGroupName();
                                    final var runtimeCommandBody = new AddMatchClientRuntimeCommandBodyDto(userId,
                                            clientId,
                                            groupName,
                                            profile);
                                    final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId,
                                            runtimeCommandBody,
                                            idempotencyKey);
                                    return syncRuntimeCommand(runtimeCommand);
                                } else {
                                    final var runtimeCommandBody = new AddClientRuntimeCommandBodyDto(userId,
                                            clientId,
                                            profile);
                                    final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId,
                                            runtimeCommandBody,
                                            idempotencyKey);
                                    return syncRuntimeCommand(runtimeCommand);
                                }
                            });
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long id) {
        final var request = new GetPlayerRequest(userId, id);
        return userShard.getService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeShard.getService().execute(request)
                .map(SyncRuntimeCommandResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", runtimeCommand, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncClientRuntimeRef(final Long clientId,
                                      final Long runtimeId,
                                      final String idempotencyKey) {
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId, runtimeId, idempotencyKey);
        final var request = new SyncClientRuntimeRefRequest(clientRuntimeRef);
        return clientShard.getService().syncClientRuntimeRef(request)
                .map(SyncClientRuntimeRefResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", clientRuntimeRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
