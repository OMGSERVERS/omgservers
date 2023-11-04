package com.omgservers.handler;

import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MulticastApprovedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.factory.MessageModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastApprovedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MULTICAST_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MulticastApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var recipients = body.getRecipients();
        final var message = body.getMessage();

        return doMulticastMessage(recipients, message)
                .replaceWith(true);
    }

    Uni<Void> doMulticastMessage(final List<Recipient> recipients,
                                 final Object message) {
        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndConcatenate(recipient -> {
                    final var userId = recipient.userId();
                    final var clientId = recipient.clientId();
                    return respondClient(userId, clientId, message);
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> respondClient(final Long userId,
                            final Long clientId,
                            final Object message) {
        final var messageBody = new ServerMessageBodyModel(message);
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.SERVER_MESSAGE, messageBody);

        final var request = RespondClientRequest.builder()
                .userId(userId)
                .clientId(clientId)
                .message(messageModel)
                .build();
        return userModule.getUserService().respondClient(request);
    }
}
