package com.omgservers.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.factory.EventModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.SignUpMessageBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignUpMessageHandlerImpl implements MessageHandler {

    final InternalModule internalModule;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.SIGN_UP_MESSAGE;
    }

    @Override
    public Uni<Void> handle(final Long connectionId, final MessageModel message) {
        final var messageBody = (SignUpMessageBodyModel) message.getBody();
        final var tenantId = messageBody.getTenantId();
        final var stageId = messageBody.getStageId();
        final var stageSecret = messageBody.getSecret();
        final var serverUri = getConfigOperation.getConfig().serverUri();

        final var eventBody = new SignUpRequestedEventBodyModel(serverUri, connectionId, tenantId, stageId, stageSecret);
        final var event = eventModelFactory.create(eventBody);
        final var request = new FireEventShardedRequest(event);
        return internalModule.getEventShardedService().fireEvent(request)
                .replaceWithVoid();
    }
}