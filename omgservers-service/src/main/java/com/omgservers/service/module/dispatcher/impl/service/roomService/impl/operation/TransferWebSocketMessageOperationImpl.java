package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.IncomingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.OutgoingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomConnection;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomInstance;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomsContainer;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferWebSocketMessageOperationImpl implements TransferWebSocketMessageOperation {

    final RoomsContainer roomsContainer;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> execute(final WebSocketConnection webSocketConnection,
                             final MessageEncodingEnum messageEncoding,
                             final String message) {

        final var roomOptional = roomsContainer.findRoom(webSocketConnection);
        if (roomOptional.isPresent()) {
            final var roomInstance = roomOptional.get();
            final var connectionOptional = roomInstance.getConnection(webSocketConnection);
            if (connectionOptional.isPresent()) {
                final var roomConnection = connectionOptional.get();
                final var role = roomConnection.getRole();
                final var clientId = roomConnection.getClientId();
                return switch (role) {
                    case RUNTIME -> transferToClients(roomInstance, messageEncoding, message);
                    case PLAYER -> transferToRuntime(roomInstance, clientId, messageEncoding, message);
                    default -> throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                            "Mismatched user role and roomInstance service, clientId=" + clientId);

                };
            } else {
                throw new ServerSideConflictException(ExceptionQualifierEnum.WRONG_ROOM_STATE);
            }
        } else {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ROOM_NOT_FOUND);
        }
    }

    Uni<Void> transferToClients(final RoomInstance room,
                                final MessageEncodingEnum messageEncoding,
                                final String runtimeMessage) {
        if (messageEncoding.equals(MessageEncodingEnum.B64)) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_WEBSOCKET_MESSAGE);
        }

        try {
            final var outgoingRuntimeMessage = objectMapper
                    .readValue(runtimeMessage, OutgoingRuntimeMessageDto.class);
            final var clients = outgoingRuntimeMessage.getClients();
            final var type = outgoingRuntimeMessage.getEncoding();
            final var message = outgoingRuntimeMessage.getMessage();

            final var clientsWebSocketConnections = room.getClientConnections(clients).stream()
                    .map(RoomConnection::getWebSocketConnection)
                    .toList();

            return Multi.createFrom().iterable(clientsWebSocketConnections)
                    .onItem().transformToUniAndConcatenate(webSocketConnection ->
                            switch (type) {
                                case TXT -> webSocketConnection.sendText(message);
                                case B64 -> {
                                    // TODO: handle decoding exception
                                    final var buffer = Base64.getDecoder().decode(message);
                                    yield webSocketConnection.sendBinary(buffer);
                                }
                            })
                    .collect().asList()
                    .replaceWithVoid();
            // TODO: fix this catch in reactive way
        } catch (IOException e) {
            log.warn("Outgoing websocket message is wrong, {}", e.getMessage());
        }

        return Uni.createFrom().voidItem();
    }

    Uni<Void> transferToRuntime(final RoomInstance room,
                                final Long clientId,
                                final MessageEncodingEnum messageEncoding,
                                final String playerMessage) {
        final var incomingRuntimeMessage = new IncomingRuntimeMessageDto(clientId, messageEncoding, playerMessage);
        final var runtimeConnection = room.getRuntimeConnection();
        if (runtimeConnection.isPresent()) {
            final var webSocketConnection = runtimeConnection.get().getWebSocketConnection();
            try {
                final var runtimeMessage = objectMapper.writeValueAsString(incomingRuntimeMessage);
                return webSocketConnection.sendText(runtimeMessage);
            } catch (IOException e) {
                throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_WEBSOCKET_MESSAGE,
                        e.getMessage(),
                        e);
            }
        } else {
            throw new ServerSideConflictException(ExceptionQualifierEnum.WRONG_ROOM_STATE);
        }
    }
}