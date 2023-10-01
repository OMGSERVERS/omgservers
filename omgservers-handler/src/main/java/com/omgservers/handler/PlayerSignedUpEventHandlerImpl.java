package com.omgservers.handler;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.SignedUpScriptEventBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerSignedUpEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final GatewayModule gatewayModule;
    final ScriptModule scriptModule;

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
                .flatMap(client -> callScript(client.getScriptId(), userId, playerId, client.getId()));
    }

    Uni<ClientModel> getClient(Long userId, Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> callScript(Long scriptId, Long userId, Long playerId, Long clientId) {
        final var scriptEventBody = SignedUpScriptEventBodyModel.builder()
                .userId(userId)
                .playerId(playerId)
                .clientId(clientId)
                .build();

        final var request = new CallScriptRequest(scriptId,
                Collections.singletonList(new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody)));
        return scriptModule.getScriptService().callScript(request)
                .map(CallScriptResponse::getResult);
    }
}
