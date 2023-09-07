package com.omgservers.handler;

import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchMessageReceivedEventBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleEventRuntimeCommandBodyModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchMessageReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchMessageReceivedEventBodyModel) event.getBody();

        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var runtimeId = body.getRuntimeId();

        final var text = body.getText();

        final var runtimeCommandBody = new HandleEventRuntimeCommandBodyModel(userId, playerId, clientId, text);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandRequest)
                .replaceWith(true);
    }
}
