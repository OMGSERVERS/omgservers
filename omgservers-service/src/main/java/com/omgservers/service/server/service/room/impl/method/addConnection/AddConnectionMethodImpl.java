package com.omgservers.service.server.service.room.impl.method.addConnection;

import com.omgservers.service.server.service.room.dto.AddConnectionRequest;
import com.omgservers.service.server.service.room.impl.component.RoomConnection;
import com.omgservers.service.server.service.room.impl.component.RoomsContainer;
import com.omgservers.service.server.service.room.impl.component.WebsocketCloseReason;
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
    public Uni<Void> addConnection(final AddConnectionRequest request) {
        log.debug("Add connection, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var webSocketConnection = request.getWebSocketConnection();
        final var roomInstance = roomsContainer.getRoom(runtimeId);

        if (roomInstance.isEmpty()) {
            log.info("Room was not found to add connection, runtimeId={}", runtimeId);
            return webSocketConnection.close(WebsocketCloseReason.ROOM_WAS_NOT_FOUND);
        }

        final var usedTokenId = request.getUsedTokenId();
        final var role = request.getRole();
        final var subject = request.getSubject();

        final var roomConnection = new RoomConnection(webSocketConnection,
                role,
                usedTokenId,
                runtimeId,
                subject);

        final var previousConnection = roomInstance.get().replaceConnection(roomConnection);
        if (previousConnection.isPresent()) {
            return webSocketConnection.close(WebsocketCloseReason.NEW_CONNECTION_WAS_OPENED)
                    .invoke(voidItem -> log.info("Previous connection was found and closed, tokenId={}", usedTokenId));
        } else {
            return Uni.createFrom().voidItem();
        }
    }
}
