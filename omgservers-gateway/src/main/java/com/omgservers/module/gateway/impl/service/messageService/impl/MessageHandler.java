package com.omgservers.module.gateway.impl.service.messageService.impl;

import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface MessageHandler {
    MessageQualifierEnum getQualifier();

    Uni<Void> handle(Long connectionId, MessageModel message);
}
