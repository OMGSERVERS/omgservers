package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.service.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatchers;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferWebSocketMessageOperationImpl implements TransferWebSocketMessageOperation {

    final TransferToPlayersOperation transferToPlayersOperation;
    final TransferToRuntimeOperation transferToRuntimeOperation;

    final ObjectMapper objectMapper;
    final Dispatchers dispatchers;

    @Override
    public Uni<Boolean> execute(final DispatcherConnection dispatcherConnection,
                                final MessageEncodingEnum messageEncoding,
                                final String message) {
        final var userRole = dispatcherConnection.getUserRole();

        final var dispatcher = dispatchers.findDispatcher(dispatcherConnection);
        if (Objects.isNull(dispatcher)) {
            return Uni.createFrom().item(Boolean.FALSE);
        }

        return switch (userRole) {
            case RUNTIME -> transferToPlayersOperation.execute(dispatcherConnection,
                    dispatcher,
                    messageEncoding,
                    message);
            case PLAYER -> {
                // It's a clientId in case of players
                final var subject = dispatcherConnection.getSubject();
                yield transferToRuntimeOperation.execute(dispatcher,
                        subject,
                        messageEncoding,
                        message);
            }
            default -> {
                log.error("Unsupported user role, {}", dispatcherConnection);
                yield Uni.createFrom().item(Boolean.FALSE);
            }
        };
    }
}