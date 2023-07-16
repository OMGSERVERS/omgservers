package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl;

import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.request.HandleMessageHelpRequest;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import com.omgservers.application.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandlerHelpServiceImpl implements HandlerHelpService {

    final Map<MessageQualifierEnum, MessageHandler> messageHandlers;

    HandlerHelpServiceImpl(Instance<MessageHandler> messageHandlerBeans) {
        this.messageHandlers = new ConcurrentHashMap<>();
        messageHandlerBeans.stream().forEach(messageHandler -> {
            final var qualifier = messageHandler.getQualifier();
            if (messageHandlers.put(qualifier, messageHandler) != null) {
                log.error("Multiple message handlers were detected, qualifier={}", qualifier);
            } else {
                log.info("Message handler was added, qualifier={}, handler={}",
                        qualifier, messageHandler.getClass().getSimpleName());
            }
        });
    }

    @Override
    public Uni<Void> handleMessage(final HandleMessageHelpRequest request) {
        HandleMessageHelpRequest.validate(request);

        final var connection = request.getConnection();
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
        return handler.handle(connection, message);
    }
}
