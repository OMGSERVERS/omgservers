package com.omgservers.module.gateway.impl.service.messageService.impl;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.module.gateway.impl.service.messageService.MessageService;
import com.omgservers.module.gateway.impl.service.messageService.request.HandleMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MessageServiceImpl implements MessageService {

    final Map<MessageQualifierEnum, MessageHandler> messageHandlers;

    MessageServiceImpl(Instance<MessageHandler> messageHandlerBeans) {
        this.messageHandlers = new ConcurrentHashMap<>();
        messageHandlerBeans.stream().forEach(messageHandler -> {
            final var qualifier = messageHandler.getQualifier();
            if (messageHandlers.put(qualifier, messageHandler) != null) {
                log.error("Multiple message handlers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> handleMessage(@Valid final HandleMessageRequest request) {
        final var connectionId = request.getConnectionId();
        final var message = request.getMessage();
        final var qualifier = message.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();
        final var body = message.getBody();

        if (!qualifierBodyClass.isInstance(body)) {
            throw new ServerSideBadRequestException("Qualifier and message body are mismatch, request=" + request);
        }

        if (!messageHandlers.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("Message handler was not found, request=" + request);
        }

        final var handler = messageHandlers.get(qualifier);
        return handler.handle(connectionId, message);
    }
}
