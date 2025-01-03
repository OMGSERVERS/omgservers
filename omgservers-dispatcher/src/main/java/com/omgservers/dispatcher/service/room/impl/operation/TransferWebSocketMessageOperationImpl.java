package com.omgservers.dispatcher.service.room.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRooms;
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
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                yield transferToPlayersOperation.execute(runtimeRoom, messageEncoding, message);
            }
            case PLAYER -> {
                final var playerRoom = dispatcherRooms.findPlayerRoom(dispatcherConnection);
                if (Objects.isNull(playerRoom)) {
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                // It's a clientId in case of players
                final var subject = dispatcherConnection.getSubject();
                yield transferToRuntimeOperation.execute(playerRoom, subject, messageEncoding, message);
            }
            default -> {
                log.error("Dispatcher connection role mismatch to transfer message, " +
                        "dispatcherConnection={}", dispatcherConnection);
                yield Uni.createFrom().item(Boolean.FALSE);
            }
        };
    }
}