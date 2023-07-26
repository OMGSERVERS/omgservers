package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.gatewayModule.GatewayModule;
import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.application.module.internalModule.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedInEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerSignedInEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final UserModule userModule;
    final LuaModule luaModule;

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
        final var getClientServiceRequest = new GetClientInternalRequest(userId, clientId);
        return userModule.getClientInternalService().getClient(getClientServiceRequest)
                .map(GetClientInternalResponse::getClient);
    }

    Uni<Void> assignPlayer(Long tenantId, Long stageId, Long userId, Long playerId, ClientModel client) {
        final var server = client.getServer();
        final var connection = client.getConnectionId();
        final var assignedPlayer = new AssignedPlayerModel(tenantId, stageId, userId, playerId, client.getId());
        final var request = new AssignPlayerInternalRequest(server, connection, assignedPlayer);
        return gatewayModule.getGatewayInternalService().assignPlayer(request);
    }

    Uni<Void> handleEvent(Long tenantId, Long stageId, Long userId, Long playerId, Long clientId) {
        final var request = new HandlePlayerSignedInEventHelpRequest(tenantId, stageId, userId, playerId, clientId);
        return luaModule.getHandlerHelpService().handlePlayerSignedInEvent(request);
    }
}
