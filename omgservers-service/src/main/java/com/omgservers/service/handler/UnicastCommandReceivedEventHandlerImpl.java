package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.UnicastCommandReceivedEventBodyModel;
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
public class UnicastCommandReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.UNICAST_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (UnicastCommandReceivedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var message = body.getMessage();

        return doUnicastMessage(runtimeId, userId, clientId, message)
                .replaceWith(true);
    }

    Uni<Boolean> doUnicastMessage(final Long runtimeId,
                                  final Long userId,
                                  final Long clientId,
                                  final Object message) {
        final var doUnicastMessageRequest = new DoUnicastMessageRequest(runtimeId, userId, clientId, message);
        return runtimeModule.getDoService().doUnicastMessage(doUnicastMessageRequest)
                .map(DoUnicastMessageResponse::getApproved);
    }
}
