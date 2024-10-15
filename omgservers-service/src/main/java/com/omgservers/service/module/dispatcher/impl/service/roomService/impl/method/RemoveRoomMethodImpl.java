package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomWebSocketCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.RoomsContainer;
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
    public Uni<RemoveRoomResponse> execute(final RemoveRoomRequest request) {
        log.debug("Remove room, request={}", request);

        final var runtimeId = request.getRuntimeId();

        final var roomInstance = roomsContainer.removeRoom(runtimeId);
        if (roomInstance.isPresent()) {
            log.info("Room was removed, runtimeId={}", runtimeId);
            final var roomConnections = roomInstance.get().getAllConnections();
            return Multi.createFrom().iterable(roomConnections)
                    .onItem().transformToUniAndConcatenate(roomConnection -> {
                        final var webSocketConnection = roomConnection.getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(RoomWebSocketCloseReason.ROOM_WAS_REMOVED);
                        } else {
                            return Uni.createFrom().voidItem();
                        }
                    })
                    .collect().asList()
                    .replaceWith(new RemoveRoomResponse(Boolean.TRUE));
        }

        return Uni.createFrom().item(new RemoveRoomResponse(Boolean.FALSE));
    }
}
