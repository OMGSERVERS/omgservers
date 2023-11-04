package com.omgservers.handler;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ChangeRequestedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.body.ChangePlayerRuntimeCommandBodyModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.factory.RuntimeCommandModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangeRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CHANGE_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ChangeRequestedEventBodyModel) event.getBody();

        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var message = body.getMessage();

        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var playerId = client.getPlayerId();
                    return getPlayer(userId, playerId)
                            .flatMap(player -> {
                                final var runtimeId = client.getDefaultRuntimeId();
                                return syncChangeRuntimeCommand(runtimeId, player, client, message);
                            });
                });
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long playerId) {
        final var request = new GetPlayerRequest(userId, playerId);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<Boolean> syncChangeRuntimeCommand(final Long runtimeId,
                                          final PlayerModel player,
                                          final ClientModel client,
                                          final Object message) {
        final var userId = client.getUserId();
        final var clientId = client.getId();
        final var playerAttributes = player.getAttributes();
        final var playerObject = player.getObject();
        final var runtimeCommandBody = new ChangePlayerRuntimeCommandBodyModel(userId,
                clientId,
                playerAttributes,
                playerObject,
                message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandShardedRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandShardedRequest)
                .map(SyncRuntimeCommandResponse::getCreated);
    }
}
