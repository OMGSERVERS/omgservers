package com.omgservers.handler;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.SignedInScriptEventBodyModel;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
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
class PlayerSignedInEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final ScriptModule scriptModule;
    final UserModule userModule;

    final MessageModelFactory messageModelFactory;

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
                .flatMap(client -> respondWelcome(userId, clientId)
                        .flatMap(voidItem -> callScript(client.getScriptId(), userId, playerId, client.getId())));
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Void> respondWelcome(final Long userId, final Long clientId) {
        final var body = new WelcomeMessageBodyModel();
        final var message = messageModelFactory.create(MessageQualifierEnum.WELCOME_MESSAGE, body);
        final var request = new RespondClientRequest(userId, clientId, message);
        return userModule.getUserService().respondClient(request);
    }

    Uni<Boolean> callScript(final Long scriptId,
                            final Long userId,
                            final Long playerId,
                            final Long clientId) {
        final var scriptEventBody = SignedInScriptEventBodyModel.builder()
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
