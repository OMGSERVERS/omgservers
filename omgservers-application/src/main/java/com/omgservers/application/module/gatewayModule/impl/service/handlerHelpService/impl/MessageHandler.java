package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl;

import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface MessageHandler {
    MessageQualifierEnum getQualifier();

    Uni<Void> handle(Long connectionId, MessageModel message);
}
