package com.omgservers.service.service.room.impl.method.handleTextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.service.room.impl.component.RoomConnection;
import com.omgservers.service.service.room.impl.component.RoomInstance;
import com.omgservers.service.service.room.impl.component.RoomsContainer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final RoomsContainer roomsContainer;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> handleTextMessage(final HandleTextMessageRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();
        final var roomOptional = roomsContainer.findRoom(webSocketConnection);
        if (roomOptional.isPresent()) {
            final var roomInstance = roomOptional.get();
            final var connectionOptional = roomInstance.getConnection(webSocketConnection);
            if (connectionOptional.isPresent()) {
                final var roomConnection = connectionOptional.get();
                final var role = roomConnection.getRole();
                final var clientId = roomConnection.getSubject();
                return switch (role) {
                    case WORKER -> transferToClients(roomInstance, message);
                    case PLAYER -> transferToRuntime(roomInstance, clientId, message);
                    default -> throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                            "Mismatched user role and roomInstance service, clientId=" + clientId);

                };
            }
        } else {
            log.warn("");
        }

        return Uni.createFrom().voidItem();
    }

    Uni<Void> transferToClients(final RoomInstance room,
                                final String outgoingMessage) {
        try {
            final var outgoingWebSocketMessage = objectMapper
                    .readValue(outgoingMessage, OutgoingWebSocketMessage.class);
            final var clients = outgoingWebSocketMessage.getClients();
            final var message = outgoingWebSocketMessage.getMessage();

            final var clientsWebSocketConnections = room.getClientConnections(clients).stream()
                    .map(RoomConnection::getWebSocketConnection)
                    .toList();

            return Multi.createFrom().iterable(clientsWebSocketConnections)
                    .onItem().transformToUniAndConcatenate(webSocketConnection -> webSocketConnection.sendText(message))
                    .collect().asList()
                    .replaceWithVoid();
        } catch (IOException e) {
            log.warn("Outgoing websocket outgoingMessage is wrong, {}", e.getMessage());
        }

        return Uni.createFrom().voidItem();
    }

    Uni<Void> transferToRuntime(final RoomInstance room,
                                final Long clientId,
                                final String incomingMessage) {
        final var incomingWebSocketMessage = new IncomingWebSocketMessage(clientId, incomingMessage);
        final var runtimeConnection = room.getRuntimeConnection();
        if (runtimeConnection.isPresent()) {
            final var webSocketConnection = runtimeConnection.get().getWebSocketConnection();
            try {
                final var messageString = objectMapper.writeValueAsString(incomingWebSocketMessage);
                return webSocketConnection.sendText(messageString);
            } catch (IOException e) {
                log.warn("Incoming message is wrong, {}:{}",
                        e.getClass().getSimpleName(), e.getMessage());
            }
        } else {
            log.error("Runtime connection was not found to transfer client message, " +
                    "runtimeId={}", room.getRuntimeId());
        }

        return Uni.createFrom().voidItem();
    }
}
