package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.message.body.ClientAssignedMessageBodyDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.module.client.client.GetClientRequest;
import com.omgservers.schema.module.client.client.GetClientResponse;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeAssignmentCreatedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final ClientShard clientShard;
    final UserShard userShard;

    final CacheService cacheService;

    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final RuntimeMessageModelFactory runtimeMessageModelFactory;

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

        final var idempotencyKey = event.getId().toString();

        return getRuntimeAssignment(runtimeId, id)
                .flatMap(runtimeAssignment -> {
                    log.debug("Created, {}", runtimeAssignment);

                    final var clientId = runtimeAssignment.getClientId();
                    return createClientAssignedRuntimeMessage(runtimeAssignment, idempotencyKey)
                            .flatMap(created -> createClientRuntimeRef(clientId, runtimeId, idempotencyKey));
                })
                .replaceWithVoid();
    }


    Uni<RuntimeAssignmentModel> getRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new GetRuntimeAssignmentRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> createClientAssignedRuntimeMessage(final RuntimeAssignmentModel runtimeAssignment,
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

                                final var runtimeAssignmentConfig = runtimeAssignment.getConfig();
                                final var groupName = runtimeAssignmentConfig.getGroupName();
                                final var messageBody = new ClientAssignedMessageBodyDto();
                                messageBody.setUserId(userId);
                                messageBody.setClientId(clientId);
                                messageBody.setProfile(profile);
                                messageBody.setGroupName(groupName);

                                final var runtimeMessage = runtimeMessageModelFactory.create(runtimeId,
                                        messageBody,
                                        idempotencyKey);
                                return syncRuntimeMessage(runtimeMessage);
                            });
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long id) {
        final var request = new GetPlayerRequest(userId, id);
        return userShard.getService().execute(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<Boolean> syncRuntimeMessage(final RuntimeMessageModel runtimeMessage) {
        final var request = new SyncRuntimeMessageRequest(runtimeMessage);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeMessageResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE);
    }

    Uni<Boolean> createClientRuntimeRef(final Long clientId,
                                        final Long runtimeId,
                                        final String idempotencyKey) {
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId, runtimeId, idempotencyKey);
        final var request = new SyncClientRuntimeRefRequest(clientRuntimeRef);
        return clientShard.getService().executeWithIdempotency(request)
                .map(SyncClientRuntimeRefResponse::getCreated);
    }
}
