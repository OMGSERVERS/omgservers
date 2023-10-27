package com.omgservers.module.gateway.impl.operation.sendMessage;

import com.omgservers.model.message.MessageModel;
import io.smallrye.mutiny.Uni;
import jakarta.websocket.Session;

public interface SendMessageOperation {
    Uni<Void> sendMessage(Session session, MessageModel message);
}
