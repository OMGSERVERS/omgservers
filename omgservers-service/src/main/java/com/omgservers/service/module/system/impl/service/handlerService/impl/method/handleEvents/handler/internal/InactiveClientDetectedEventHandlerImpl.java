package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.internal;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.InactiveClientDetectedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.DisconnectionMessageBodyModel;
import com.omgservers.model.message.body.DisconnectionReasonEnum;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InactiveClientDetectedEventHandlerImpl implements EventHandler {

    final ClientModule clientModule;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INACTIVE_CLIENT_DETECTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (InactiveClientDetectedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return clientModule.getShortcutService().getClient(clientId)
                .flatMap(client -> {
                    if (client.getDeleted()) {
                        log.warn("Client was already deleted, " +
                                "disconnection message won't be created, clientId={}", clientId);
                        return Uni.createFrom().item(true);
                    } else {
                        log.info("Inactive client was detected, clientId={}", clientId);

                        return syncDisconnectionMessage(clientId)
                                .flatMap(created -> clientModule.getShortcutService().deleteClient(clientId));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Boolean> syncDisconnectionMessage(final Long clientId) {
        final var messageBody = new DisconnectionMessageBodyModel(DisconnectionReasonEnum.CLIENT_INACTIVITY);
        final var disconnectionMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.DISCONNECTION_MESSAGE,
                messageBody);
        return clientModule.getShortcutService().syncClientMessage(disconnectionMessage);
    }
}
