package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl;

import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface MessageHandler {
    MessageQualifierEnum getQualifier();

    Uni<Void> handle(Long connectionId, MessageModel message);
}
