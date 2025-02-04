package com.omgservers.dispatcher.service.room.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.service.room.dto.OutgoingRuntimeMessageDto;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ParseRuntimeOutgoingMessageOperationImpl implements ParseRuntimeOutgoingMessageOperation {

    final ObjectMapper objectMapper;

    @Override
    public Optional<OutgoingRuntimeMessageDto> execute(final DispatcherRoom room, final String runtimeMessage) {

        final OutgoingRuntimeMessageDto outgoingRuntimeMessage;
        try {
            outgoingRuntimeMessage = objectMapper.readValue(runtimeMessage, OutgoingRuntimeMessageDto.class);
        } catch (IOException e) {
            log.warn("Wrong outgoing runtime message was received, message couldn't be parsed, room={}, {}:{}",
                    room, e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }

        final var encoding = outgoingRuntimeMessage.getEncoding();
        final var message = outgoingRuntimeMessage.getMessage();

        if (Objects.isNull(encoding)) {
            log.warn("Wrong message was received, encoding is not set, runtimeId={}",
                    room.getRuntimeId());
            return Optional.empty();
        }

        if (Objects.isNull(message)) {
            log.warn("Wrong message was received, message is null, runtimeId={}",
                    room.getRuntimeId());
            return Optional.empty();
        }

        if (message.isEmpty()) {
            log.warn("Wrong outgoing runtime message was received, message is empty, runtimeId={}",
                    room.getRuntimeId());
            return Optional.empty();
        }

        return Optional.of(outgoingRuntimeMessage);
    }
}
