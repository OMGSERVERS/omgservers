package com.omgservers.service.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.service.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.body.SignUpMessageReceivedEventBodyModel;
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

    final SystemModule systemModule;

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

        final var eventBody = new SignUpMessageReceivedEventBodyModel(serverUri, connectionId, tenantId, stageId, stageSecret);
        final var event = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(event);
        return systemModule.getEventService().syncEvent(request)
                .replaceWithVoid();
    }
}
