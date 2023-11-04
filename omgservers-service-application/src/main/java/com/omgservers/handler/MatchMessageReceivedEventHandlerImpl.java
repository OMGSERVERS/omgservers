package com.omgservers.handler;

import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchRequestedEventBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.factory.RuntimeCommandModelFactory;
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
        return EventQualifierEnum.MATCH_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchRequestedEventBodyModel) event.getBody();

        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var runtimeId = body.getRuntimeId();

        final var data = body.getData();

        final var runtimeCommandBody = new HandleMessageRuntimeCommandBodyModel(userId, clientId, data);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandRequest)
                .replaceWith(true);
    }
}
