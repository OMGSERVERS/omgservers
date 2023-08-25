package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl.messageHandler;

import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl.MessageHandler;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
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

    final InternalModule internalModule;

    final GetConfigOperation getConfigOperation;

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

        final var event = new SignInRequestedEventBodyModel(serverUri, connectionId, tenant, stage, stageSecret, user, userPassword);
        final var request = new FireEventHelpRequest(event);
        return internalModule.getEventHelpService().fireEvent(request)
                .replaceWithVoid();
    }
}
