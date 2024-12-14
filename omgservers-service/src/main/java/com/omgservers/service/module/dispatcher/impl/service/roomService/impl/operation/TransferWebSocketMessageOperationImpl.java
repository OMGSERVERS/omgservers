package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
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
                    log.debug("Room was not found to transfer runtime message, dispatcherConnection={}",
                            dispatcherConnection);
                    yield Uni.createFrom().item(Boolean.FALSE);
                }

                yield transferToPlayersOperation.execute(runtimeRoom, messageEncoding, message);
            }
            case PLAYER -> {
                final var playerRoom = dispatcherRooms.findPlayerRoom(dispatcherConnection);
                if (Objects.isNull(playerRoom)) {
                    log.debug("Room was not found to transfer player message, dispatcherConnection={}",
                            dispatcherConnection);
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