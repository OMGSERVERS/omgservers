package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.gatewayModule.GatewayModule;
import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.model.client.ClientModel;
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
        final var tenant = body.getTenant();
        final var stage = body.getStage();
        final var user = body.getUser();
        final var player = body.getPlayer();
        final var clientUuid = body.getClient();

        return getClient(user, clientUuid)
                .flatMap(client -> assignPlayer(tenant, stage, user, player, client))
                .flatMap(voidItem -> handleEvent(tenant, stage, user, player, clientUuid))
                .replaceWith(true);
    }

    Uni<ClientModel> getClient(UUID user, UUID client) {
        final var getClientServiceRequest = new GetClientInternalRequest(user, client);
        return userModule.getClientInternalService().getClient(getClientServiceRequest)
                .map(GetClientInternalResponse::getClient);
    }

    Uni<Void> assignPlayer(UUID tenant, UUID stage, UUID user, UUID player, ClientModel client) {
        final var server = client.getServer();
        final var connection = client.getConnection();
        final var assignedPlayer = new AssignedPlayerModel(tenant, stage, user, player, client.getUuid());
        final var request = new AssignPlayerInternalRequest(server, connection, assignedPlayer);
        return gatewayModule.getGatewayInternalService().assignPlayer(request);
    }

    Uni<Void> handleEvent(UUID tenant, UUID stage, UUID user, UUID player, UUID client) {
        final var request = new HandlePlayerSignedUpEventHelpRequest(tenant, stage, user, player, client);
        return luaModule.getHandlerHelpService().handlePlayerSignedUpEvent(request);
    }
}
