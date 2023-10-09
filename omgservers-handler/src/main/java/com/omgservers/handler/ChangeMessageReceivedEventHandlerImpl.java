package com.omgservers.handler;

import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ChangeRequestedEventBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.ChangePlayerScriptEventBodyModel;
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
public class ChangeMessageReceivedEventHandlerImpl implements EventHandler {

    final ScriptModule scriptModule;
    final UserModule userModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CHANGE_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ChangeRequestedEventBodyModel) event.getBody();

        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var data = body.getData();

        return getClient(userId, clientId)
                .flatMap(client -> callScript(client.getScriptId(), userId, playerId, client.getId(), data));
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> callScript(final Long scriptId,
                            final Long userId,
                            final Long playerId,
                            final Long clientId,
                            final Object data) {
        final var scriptEventBody = ChangePlayerScriptEventBodyModel.builder()
                .userId(userId)
                .playerId(playerId)
                .clientId(clientId)
                .data(data)
                .build();

        final var request = new CallScriptRequest(scriptId,
                Collections.singletonList(new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody)));
        return scriptModule.getScriptService().callScript(request)
                .map(CallScriptResponse::getResult);
    }
}
