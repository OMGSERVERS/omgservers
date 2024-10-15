package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomConnection;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomWebSocketCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomsContainer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AddConnectionMethodImpl implements AddConnectionMethod {

    final RoomsContainer roomsContainer;

    @Override
    public Uni<Void> execute(final AddConnectionRequest request) {
        log.debug("Add connection, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var webSocketConnection = request.getWebSocketConnection();
        final var roomInstance = roomsContainer.getRoom(runtimeId);

        if (roomInstance.isEmpty()) {
            log.warn("Room was not found to add connection, runtimeId={}, role={}",
                    runtimeId, request.getRole());
            return webSocketConnection.close(RoomWebSocketCloseReason.ROOM_WAS_NOT_FOUND);
        }

        final var usedTokenId = request.getUsedTokenId();
        final var role = request.getRole();
        final var clientId = request.getClientId();

        final var roomConnection = new RoomConnection(webSocketConnection,
                role,
                usedTokenId,
                runtimeId,
                clientId);

        final var previousConnection = roomInstance.get().replaceConnection(roomConnection);
        if (previousConnection.isPresent()) {
            final var previousWebSocketConnection = previousConnection.get().getWebSocketConnection();
            if (previousWebSocketConnection.isOpen()) {
                return previousWebSocketConnection.close(RoomWebSocketCloseReason.NEW_CONNECTION_WAS_OPENED)
                        .invoke(voidItem -> log.info("Previous connection was found and closed, tokenId={}",
                                usedTokenId));
            }
        }

        return Uni.createFrom().voidItem();
    }
}
