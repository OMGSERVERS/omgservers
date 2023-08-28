package com.omgservers.handler;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerSignedInEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final UserModule userModule;
    final ContextModule contextModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PLAYER_SIGNED_IN;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (PlayerSignedInEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();

        return getClient(userId, clientId)
                .flatMap(client -> assignPlayer(tenantId, stageId, userId, playerId, client))
                .flatMap(voidItem -> handleEvent(tenantId, stageId, userId, playerId, clientId))
                .replaceWith(true);
    }

    Uni<ClientModel> getClient(Long userId, Long clientId) {
        final var getClientServiceRequest = new GetClientShardedRequest(userId, clientId);
        return userModule.getClientShardedService().getClient(getClientServiceRequest)
                .map(GetClientShardedResponse::getClient);
    }

    Uni<Void> assignPlayer(Long tenantId, Long stageId, Long userId, Long playerId, ClientModel client) {
        final var server = client.getServer();
        final var connection = client.getConnectionId();
        final var assignedPlayer = new AssignedPlayerModel(tenantId, stageId, userId, playerId, client.getId());
        final var request = new AssignPlayerRoutedRequest(server, connection, assignedPlayer);
        return gatewayModule.getGatewayService().assignPlayer(request);
    }

    Uni<Void> handleEvent(Long tenantId, Long stageId, Long userId, Long playerId, Long clientId) {
        final var request = new HandlePlayerSignedInEventRequest(tenantId, stageId, userId, playerId, clientId);
        return contextModule.getContextService().handlePlayerSignedInEvent(request);
    }
}
