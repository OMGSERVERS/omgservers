package com.omgservers.service.handler.runtime;

import com.omgservers.schema.model.client.ClientModel;
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
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
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

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final UserModule userModule;

    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeAssignmentCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return getRuntimeAssignment(runtimeId, id)
                .flatMap(runtimeAssignment -> {
                    final var clientId = runtimeAssignment.getClientId();

                    log.info("Runtime assignment was created, runtimeAssignment={}/{}, clientId={}",
                            runtimeId, runtimeAssignment.getId(), clientId);

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
        return runtimeModule.getRuntimeService().getRuntimeAssignment(request)
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
                                final var attributes = player.getAttributes();
                                final var profile = player.getProfile();

                                final var runtimeId = runtimeAssignment.getRuntimeId();

                                if (Objects.nonNull(runtimeAssignment.getConfig().getMatchClient())) {
                                    final var groupName = runtimeAssignment.getConfig().getMatchClient().getGroupName();
                                    final var runtimeCommandBody = new AddMatchClientRuntimeCommandBodyModel(clientId,
                                            groupName,
                                            attributes,
                                            profile);
                                    final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId,
                                            runtimeCommandBody,
                                            idempotencyKey);
                                    return syncRuntimeCommand(runtimeCommand);
                                } else {
                                    final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(clientId,
                                            attributes,
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
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long id) {
        final var request = new GetPlayerRequest(userId, id);
        return userModule.getUserService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(request)
                .map(SyncRuntimeCommandResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeCommand, t.getMessage());
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
        return clientModule.getClientService().syncClientRuntimeRef(request)
                .map(SyncClientRuntimeRefResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", clientRuntimeRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
