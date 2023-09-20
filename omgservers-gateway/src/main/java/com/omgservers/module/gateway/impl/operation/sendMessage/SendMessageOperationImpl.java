package com.omgservers.module.gateway.impl.operation.sendMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.message.MessageModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SendMessageOperationImpl implements SendMessageOperation {

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> sendMessage(final Session session, final MessageModel message) {
        if (session == null) {
            throw new ServerSideBadRequestException("session is null");
        }
        if (message == null) {
            throw new ServerSideBadRequestException("message is null");
        }

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    try {
                        final var textMessage = objectMapper.writeValueAsString(message);
                        session.getBasicRemote().sendText(textMessage);
                        log.info("Message was sent, sessionId={}, qualifier={}", session.getId(),
                                message.getQualifier());
                    } catch (IOException e) {
                        log.warn("Unable to send message, {}, qualifier={}", e.getMessage(), message.getQualifier());
                    }
                });
    }
}
