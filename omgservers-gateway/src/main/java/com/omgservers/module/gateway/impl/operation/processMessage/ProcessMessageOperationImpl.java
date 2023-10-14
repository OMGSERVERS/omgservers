package com.omgservers.module.gateway.impl.operation.processMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.message.MessageModel;
import com.omgservers.module.gateway.impl.service.messageService.MessageService;
import com.omgservers.module.gateway.impl.service.messageService.request.HandleMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ProcessMessageOperationImpl implements ProcessMessageOperation {

    final MessageService messageService;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> processMessage(final Long connectionId, final String messageString) {
        try {
            final var message = objectMapper.readValue(messageString, MessageModel.class);
            final var handleMessageRequest = new HandleMessageRequest(connectionId, message);
            return messageService.handleMessage(handleMessageRequest);
        } catch (Exception e) {
            throw new ServerSideBadRequestException(String
                    .format("wrong message, connectionId=%s, %s", connectionId, e.getMessage()));
        }
    }
}
