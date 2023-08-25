package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.application.module.gatewayModule.GatewayModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerSignedUpEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final GatewayModule gatewayModule;
    final LuaModule luaModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PLAYER_SIGNED_UP;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (PlayerSignedUpEventBodyModel) event.getBody();
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
        final var getClientServiceRequest = new GetClientInternalRequest(userId, clientId);
        return userModule.getClientInternalService().getClient(getClientServiceRequest)
                .map(GetClientInternalResponse::getClient);
    }

    Uni<Void> assignPlayer(Long tenantId, Long stageId, Long userId, Long playerId, ClientModel client) {
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var assignedPlayer = new AssignedPlayerModel(tenantId, stageId, userId, playerId, client.getId());
        final var request = new AssignPlayerInternalRequest(server, connectionId, assignedPlayer);
        return gatewayModule.getGatewayInternalService().assignPlayer(request);
    }

    Uni<Void> handleEvent(Long tenantId, Long stageId, Long userId, Long playerId, Long clientId) {
        final var request = new HandlePlayerSignedUpEventHelpRequest(tenantId, stageId, userId, playerId, clientId);
        return luaModule.getHandlerHelpService().handlePlayerSignedUpEvent(request);
    }
}
