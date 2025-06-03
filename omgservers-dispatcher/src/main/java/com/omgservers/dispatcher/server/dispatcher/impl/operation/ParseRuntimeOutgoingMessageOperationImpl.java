package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.server.dispatcher.dto.OutgoingRuntimeMessageDto;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ParseRuntimeOutgoingMessageOperationImpl implements ParseRuntimeOutgoingMessageOperation {

    final ObjectMapper objectMapper;
    final Validator validator;

    @Override
    public Optional<OutgoingRuntimeMessageDto> execute(final Dispatcher dispatcher,
                                                       final String runtimeMessage) {
        final OutgoingRuntimeMessageDto outgoingRuntimeMessage;

        try {
            outgoingRuntimeMessage = objectMapper.readValue(runtimeMessage, OutgoingRuntimeMessageDto.class);
        } catch (IOException e) {
            log.warn("Message from runtime \"{}\" couldn't be parsed, {}:{}",
                    dispatcher.getRuntimeId(),
                    e.getClass().getSimpleName(),
                    e.getMessage());
            return Optional.empty();
        }

        final var constraints = validator.validate(outgoingRuntimeMessage);
        if (!constraints.isEmpty()) {
            log.warn("Runtime \"{}\" message validation failed", dispatcher.getRuntimeId());
            return Optional.empty();
        }

        return Optional.of(outgoingRuntimeMessage);
    }
}
