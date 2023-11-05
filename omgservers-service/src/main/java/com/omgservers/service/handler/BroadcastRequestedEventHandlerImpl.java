package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.BroadcastCommandReceivedEventBodyModel;
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
public class BroadcastRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.BROADCAST_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (BroadcastCommandReceivedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var message = body.getMessage();

        return doBroadcastMessage(runtimeId, message)
                .replaceWith(true);
    }

    Uni<Boolean> doBroadcastMessage(final Long runtimeId,
                                    final Object message) {
        final var doBroadcastMessageRequest = new DoBroadcastMessageRequest(runtimeId, message);
        return runtimeModule.getDoService().doBroadcastMessage(doBroadcastMessageRequest)
                .map(DoBroadcastMessageResponse::getApproved);
    }
}
