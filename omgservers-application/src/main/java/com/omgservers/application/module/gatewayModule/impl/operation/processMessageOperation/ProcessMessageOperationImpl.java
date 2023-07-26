package com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.request.HandleMessageHelpRequest;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ProcessMessageOperationImpl implements ProcessMessageOperation {

    final HandlerHelpService handlerHelpService;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> processMessage(final Long connectionId, final String messageString) {
        try {
            final var message = objectMapper.readValue(messageString, MessageModel.class);
            log.info("Handle message, connectionId={}, message={}", connectionId, message);
            final var handleMessageServiceRequest = new HandleMessageHelpRequest(connectionId, message);
            return handlerHelpService.handleMessage(handleMessageServiceRequest);
        } catch (Exception e) {
            throw new ServerSideBadRequestException(String
                    .format("wrong message, connectionId=%s, %s", connectionId, e.getMessage()));
        }
    }
}
