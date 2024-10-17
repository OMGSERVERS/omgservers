package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.OutgoingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TransferToPlayersOperationImpl implements TransferToPlayersOperation {

    final ParseRuntimeOutgoingMessageOperation parseRuntimeOutgoingMessageOperation;
    final SendRuntimeBinaryMessageOperation sendRuntimeBinaryMessageOperation;
    final SendRuntimeTextMessageOperation sendRuntimeTextMessageOperation;

    @Override
    public Uni<Boolean> execute(final DispatcherRoom room,
                                final MessageEncodingEnum messageEncoding,
                                final String runtimeMessage) {
        if (messageEncoding.equals(MessageEncodingEnum.B64)) {
            log.error("Wrong runtime outgoing message encoding, room={}, messageEncoding={}",
                    room, messageEncoding);
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final Optional<OutgoingRuntimeMessageDto> outgoingMessageOptional = parseRuntimeOutgoingMessageOperation
                .execute(room, runtimeMessage);
        if (outgoingMessageOptional.isEmpty()) {
            return Uni.createFrom().item(Boolean.FALSE);
        }
        final var outgoingMessage = outgoingMessageOptional.get();

        final var clients = outgoingMessage.getClients();
        final var encoding = outgoingMessage.getEncoding();
        final var message = outgoingMessage.getMessage();

        final var playerConnections = room.filterPlayerConnections(clients);

        return switch (encoding) {
            case TXT -> sendRuntimeTextMessageOperation.execute(room, playerConnections, message);
            case B64 -> sendRuntimeBinaryMessageOperation.execute(room, playerConnections, message);
        };
    }
}
