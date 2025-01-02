package com.omgservers.dispatcher.service.room.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.service.room.dto.IncomingRuntimeMessageDto;
import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
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
    public Uni<Boolean> execute(final DispatcherRoom room,
                                final Long clientId,
                                final MessageEncodingEnum messageEncoding,
                                final String playerMessage) {
        final var incomingRuntimeMessage = new IncomingRuntimeMessageDto(clientId, messageEncoding, playerMessage);
        final var runtimeConnection = room.getRuntimeConnection();

        try {
            final var runtimeMessage = objectMapper.writeValueAsString(incomingRuntimeMessage);
            return runtimeConnection.sendText(runtimeMessage)
                    .replaceWith(Boolean.TRUE)
                    .onFailure().recoverWithUni(t -> {
                        log.warn("Failed to send incoming runtime message, " +
                                        "room={}, runtimeConnection={}, {}:{}",
                                room, runtimeConnection, t.getClass().getSimpleName(), t.getMessage());
                        // It's a runtime issue, not a client, but we fail client anymore
                        return Uni.createFrom().item(Boolean.FALSE);
                    });
        } catch (IOException e) {
            log.warn("Incoming runtime message DTO couldn't be written, room={}, clientId={}, {}:{}",
                    room, clientId, e.getClass().getSimpleName(), e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
