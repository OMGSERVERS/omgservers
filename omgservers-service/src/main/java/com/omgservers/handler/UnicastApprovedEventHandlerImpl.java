package com.omgservers.handler;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.UnicastApprovedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UnicastApprovedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.UNICAST_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (UnicastApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var message = body.getMessage();

        return respondClient(userId, clientId, message)
                .replaceWith(true);
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
