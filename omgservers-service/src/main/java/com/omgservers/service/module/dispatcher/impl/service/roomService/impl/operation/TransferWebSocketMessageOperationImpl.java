package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.IncomingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.OutgoingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferWebSocketMessageOperationImpl implements TransferWebSocketMessageOperation {

    final DispatcherConnections dispatcherConnections;
    final DispatcherRooms dispatcherRooms;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final DispatcherConnection dispatcherConnection,
                                final MessageEncodingEnum messageEncoding,
                                final String message) {
        final var userRole = dispatcherConnection.getUserRole();

        return switch (userRole) {
            case RUNTIME -> {
                final var runtimeRoom = dispatcherRooms.findRuntimeRoom(dispatcherConnection);
                if (Objects.isNull(runtimeRoom)) {
                    log.info("Room was not found to transfer runtime message, dispatcherConnection={}",
                            dispatcherConnection);
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                yield transferToPlayers(runtimeRoom, messageEncoding, message);
            }
            case PLAYER -> {
                final var playerRoom = dispatcherRooms.findPlayerRoom(dispatcherConnection);
                if (Objects.isNull(playerRoom)) {
                    log.info("Room was not found to transfer player message, dispatcherConnection={}",
                            dispatcherConnection);
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                // It's a clientId in case of players
                final var subject = dispatcherConnection.getSubject();
                yield transferToRuntime(playerRoom, subject, messageEncoding, message);
            }
            default -> {
                log.error("Dispatcher connection role mismatch to transfer message, " +
                        "dispatcherConnection={}", dispatcherConnection);
                yield Uni.createFrom().item(Boolean.FALSE);
            }
        };
    }

    Uni<Boolean> transferToPlayers(final DispatcherRoom room,
                                   final MessageEncodingEnum messageEncoding,
                                   final String runtimeMessage) {
        if (messageEncoding.equals(MessageEncodingEnum.B64)) {
            log.error("Wrong runtime outgoing message encoding, runtimeId={}, messageEncoding={}", room.getRuntimeId(),
                    messageEncoding);
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final OutgoingRuntimeMessageDto outgoingRuntimeMessage;
        try {
            outgoingRuntimeMessage = objectMapper.readValue(runtimeMessage, OutgoingRuntimeMessageDto.class);
        } catch (IOException e) {
            log.warn("Wrong outgoing runtime message was received, message couldn't be parsed, " +
                            "runtimeId={}, {}:{}",
                    room.getRuntimeId(), e.getClass().getSimpleName(), e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final var clients = outgoingRuntimeMessage.getClients();
        final var encoding = outgoingRuntimeMessage.getEncoding();
        final var message = outgoingRuntimeMessage.getMessage();

        if (Objects.isNull(clients) || Objects.isNull(encoding) || Objects.isNull(message)) {
            log.warn("Wrong outgoing runtime message was received, some fields are null, runtimeId={}",
                    room.getRuntimeId());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        if (clients.isEmpty()) {
            log.warn("Wrong outgoing runtime message was received, clients list is empty, runtimeId={}",
                    room.getRuntimeId());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        if (message.isEmpty()) {
            log.warn("Wrong outgoing runtime message was received, message is empty, runtimeId={}",
                    room.getRuntimeId());
            return Uni.createFrom().item(Boolean.FALSE);
        }

        final var webSocketConnections = room.filterPlayerConnections(clients).stream()
                .map(DispatcherConnection::getWebSocketConnection)
                .toList();

        return switch (encoding) {
            case TXT -> Multi.createFrom().iterable(webSocketConnections)
                    .onItem().transformToUniAndConcatenate(webSocketConnection ->
                            webSocketConnection.sendText(message))
                    .collect().asList()
                    .replaceWith(Boolean.TRUE);
            case B64 -> {
                final byte[] buffer;
                try {
                    buffer = Base64.getDecoder().decode(message);
                } catch (IllegalArgumentException e) {
                    log.warn(
                            "Wrong outgoing runtime message was received, message field couldn't be decoded runtimeId={}, {}:{}",
                            room.getRuntimeId(), e.getClass().getSimpleName(), e.getMessage());
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                yield Multi.createFrom().iterable(webSocketConnections)
                        .onItem().transformToUniAndConcatenate(webSocketConnection ->
                                webSocketConnection.sendBinary(buffer))
                        .collect().asList()
                        .replaceWith(Boolean.TRUE);
            }
        };
    }

    Uni<Boolean> transferToRuntime(final DispatcherRoom room,
                                   final Long clientId,
                                   final MessageEncodingEnum messageEncoding,
                                   final String playerMessage) {
        final var incomingRuntimeMessage = new IncomingRuntimeMessageDto(clientId, messageEncoding, playerMessage);
        final var runtimeConnection = room.getRuntimeConnection();
        final var webSocketConnection = runtimeConnection.getWebSocketConnection();

        try {
            final var runtimeMessage = objectMapper.writeValueAsString(incomingRuntimeMessage);
            return webSocketConnection.sendText(runtimeMessage)
                    .replaceWith(Boolean.TRUE);
        } catch (IOException e) {
            log.warn("Incoming runtime message DTO couldn't be written, clientId={}, {}:{}",
                    clientId, e.getClass().getSimpleName(), e.getMessage());
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}