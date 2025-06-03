package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TransferToPlayersOperationImpl implements TransferToPlayersOperation {

    final ParseRuntimeOutgoingMessageOperation parseRuntimeOutgoingMessageOperation;
    final SendRuntimeBinaryMessageOperation sendRuntimeBinaryMessageOperation;
    final SendRuntimeTextMessageOperation sendRuntimeTextMessageOperation;

    @Override
    public Uni<Boolean> execute(final DispatcherConnection dispatcherConnection,
                                final Dispatcher dispatcher,
                                final MessageEncodingEnum messageEncoding,
                                final String runtimeMessage) {
        if (!messageEncoding.equals(MessageEncodingEnum.TXT)) {
            log.error("Unsupported message encoding from runtime \"{}\", messageEncoding={}",
                    messageEncoding, dispatcher.getRuntimeId());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final var outgoingMessageOptional = parseRuntimeOutgoingMessageOperation.execute(dispatcher, runtimeMessage);
        if (outgoingMessageOptional.isEmpty()) {
            return Uni.createFrom().item(Boolean.FALSE);
        }
        final var outgoingMessage = outgoingMessageOptional.get();

        final var clients = outgoingMessage.getClients();
        final var encoding = outgoingMessage.getEncoding();
        final var message = outgoingMessage.getMessage();

        final List<DispatcherConnection> playerConnections;
        // Broadcast message
        if (Objects.isNull(clients)) {
            playerConnections = dispatcher.getAllPlayerConnections();
        } else {
            // Multicast message
            playerConnections = dispatcher.filterPlayerConnections(clients);
        }

        return switch (encoding) {
            case TXT -> sendRuntimeTextMessageOperation.execute(dispatcher, playerConnections, message);
            case B64 -> sendRuntimeBinaryMessageOperation.execute(dispatcher, playerConnections, message);
        };
    }
}
