package com.omgservers.service.handler;

import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.UnicastCommandApprovedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UnicastCommandApprovedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.UNICAST_COMMAND_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (UnicastCommandApprovedEventBodyModel) event.getBody();
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

        final var request = new RespondClientRequest(userId, clientId, messageModel);
        return userModule.getUserService().respondClient(request);
    }
}
