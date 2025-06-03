package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.server.dispatcher.dto.IncomingRuntimeMessageDto;
import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TransferToRuntimeOperationImpl implements TransferToRuntimeOperation {

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final Dispatcher dispatcher,
                                final Long clientId,
                                final MessageEncodingEnum messageEncoding,
                                final String playerMessage) {
        final var incomingRuntimeMessage = new IncomingRuntimeMessageDto(clientId, messageEncoding, playerMessage);
        final var runtimeConnection = dispatcher.getRuntimeConnection();
        final var runtimeId = runtimeConnection.getRuntimeId();

        try {
            final var runtimeMessage = objectMapper.writeValueAsString(incomingRuntimeMessage);
            return runtimeConnection.sendText(runtimeMessage)
                    .replaceWith(Boolean.TRUE)
                    .onFailure().recoverWithUni(t -> {
                        log.error("Failed to send client \"{}\" message to runtime \"{}\", {}:{}",
                                clientId,
                                runtimeId,
                                t.getClass().getSimpleName(),
                                t.getMessage());
                        return Uni.createFrom().item(Boolean.FALSE);
                    });
        } catch (IOException e) {
            log.warn("Message from client \"{}\" to runtime \"{}\" couldn't be written, {}:{}",
                    clientId,
                    runtimeId,
                    e.getClass().getSimpleName(),
                    e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
