package com.omgservers.service.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.service.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.body.SignInMessageReceivedEventBodyModel;
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
        log.debug("Handle sign in message, connectionId={}, message={}", connectionId, message);

        final var messageBody = (SignInMessageBodyModel) message.getBody();
        final var tenant = messageBody.getTenantId();
        final var stage = messageBody.getStageId();
        final var stageSecret = messageBody.getSecret();
        final var user = messageBody.getUserId();
        final var userPassword = messageBody.getPassword();
        final var serverUri = getConfigOperation.getConfig().externalUri();

        final var eventBody = new SignInMessageReceivedEventBodyModel(serverUri, connectionId, tenant, stage, stageSecret, user, userPassword);
        final var event = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(event);
        return systemModule.getEventService().syncEvent(request)
                .replaceWithVoid();
    }
}
