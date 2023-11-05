package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ChangeCommandReceivedEventBodyModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangeCommandReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CHANGE_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ChangeCommandReceivedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var message = body.getMessage();

        return doChangePlayer(runtimeId, userId, clientId, message)
                .replaceWith(true);
    }

    Uni<Boolean> doChangePlayer(final Long runtimeId,
                                final Long userId,
                                final Long clientId,
                                final Object message) {
        final var doChangePlayerRequest = new DoChangePlayerRequest(runtimeId, userId, clientId, message);
        return runtimeModule.getDoService().doChangePlayer(doChangePlayerRequest)
                .map(DoChangePlayerResponse::getApproved);
    }
}
