package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MulticastCommandReceivedEventBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastCommandReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MULTICAST_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MulticastCommandReceivedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var recipients = body.getRecipients();
        final var message = body.getMessage();

        return doMulticastMessage(runtimeId, recipients, message)
                .replaceWith(true);
    }

    Uni<Boolean> doMulticastMessage(final Long runtimeId,
                                    final List<Recipient> recipients,
                                    final Object message) {
        final var doMulticastMessageRequest = new DoMulticastMessageRequest(runtimeId, recipients, message);
        return runtimeModule.getDoService().doMulticastMessage(doMulticastMessageRequest)
                .map(DoMulticastMessageResponse::getApproved);
    }
}
