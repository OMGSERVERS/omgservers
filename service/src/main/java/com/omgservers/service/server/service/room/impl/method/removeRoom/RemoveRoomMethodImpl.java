package com.omgservers.service.server.service.room.impl.method.removeRoom;

import com.omgservers.service.server.service.room.dto.RemoveRoomRequest;
import com.omgservers.service.server.service.room.dto.RemoveRoomRequest;
import com.omgservers.service.server.service.room.impl.component.RoomsContainer;
import com.omgservers.service.server.service.room.impl.component.WebsocketCloseReason;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RemoveRoomMethodImpl implements RemoveRoomMethod {

    final RoomsContainer roomsContainer;

    @Override
    public Uni<Void> removeRoom(final RemoveRoomRequest request) {
        log.debug("Remove room, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var roomInstance = roomsContainer.removeRoom(runtimeId);
        if (roomInstance.isPresent()) {
            final var roomConnections = roomInstance.get().getAllConnections();
            return Multi.createFrom().iterable(roomConnections)
                    .onItem().transformToUniAndConcatenate(roomConnection -> {
                        final var webSocketConnection = roomConnection.getWebSocketConnection();
                        return webSocketConnection.close(WebsocketCloseReason.ROOM_WAS_REMOVED);
                    })
                    .collect().asList()
                    .replaceWithVoid();
        }

        return Uni.createFrom().voidItem();
    }
}
