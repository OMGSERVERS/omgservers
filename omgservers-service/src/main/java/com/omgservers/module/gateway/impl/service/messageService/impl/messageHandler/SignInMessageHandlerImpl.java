package com.omgservers.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.dto.internal.SyncEventRequest;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.SignInMessageBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignInMessageHandlerImpl implements MessageHandler {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.SIGN_IN_MESSAGE;
    }

    @Override
    public Uni<Void> handle(Long connectionId, MessageModel message) {
        final var messageBody = (SignInMessageBodyModel) message.getBody();
        final var tenant = messageBody.getTenantId();
        final var stage = messageBody.getStageId();
        final var stageSecret = messageBody.getSecret();
        final var user = messageBody.getUserId();
        final var userPassword = messageBody.getPassword();
        final var serverUri = getConfigOperation.getConfig().serverUri();

        final var eventBody = new SignInRequestedEventBodyModel(serverUri, connectionId, tenant, stage, stageSecret, user, userPassword);
        final var event = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(event);
        return systemModule.getEventService().syncEvent(request)
                .replaceWithVoid();
    }
}
