package com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.request.HandleMessageHelpRequest;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
class ProcessMessageOperationImpl implements ProcessMessageOperation {

    final BroadcastProcessor<Tuple2<UUID, String>> messageProcessor;

    public ProcessMessageOperationImpl(HandlerHelpService handlerHelpService, ObjectMapper objectMapper) {
        messageProcessor = BroadcastProcessor.create();
        messageProcessor.onItem().transformToUniAndConcatenate(tuple -> {
                    final var connection = tuple.getItem1();
                    final var messageString = tuple.getItem2();
                    try {
                        final var message = objectMapper.readValue(messageString, MessageModel.class);
                        log.info("Handle message, connection={}, message={}", connection, message);
                        final var handleMessageServiceRequest = new HandleMessageHelpRequest(connection, message);
                        return handlerHelpService.handleMessage(handleMessageServiceRequest)
                                .replaceWith(messageString);
                    } catch (Exception e) {
                        return Uni.createFrom().item(messageString);
                    }
                })
                .subscribe().with(messageString -> log.info("Message was handled, {}", messageString),
                        failure -> log.info("Message handler failed, {}", failure.getMessage()));
    }

    @Override
    public void processMessage(final UUID connection, final String messageString) {
        final var tuple = Tuple2.of(connection, messageString);
        messageProcessor.onNext(tuple);
    }
}
