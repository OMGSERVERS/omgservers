package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomRequest;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomResponse;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRooms;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RemoveRoomMethodImpl implements RemoveRoomMethod {

    final DispatcherRooms dispatcherRooms;

    @Override
    public Uni<RemoveRoomResponse> execute(final RemoveRoomRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();

        final var dispatcherRoom = dispatcherRooms.removeRoom(runtimeId);
        if (Objects.nonNull(dispatcherRoom)) {
            final var allPlayerConnections = dispatcherRoom.getAllPlayerConnections();

            if (allPlayerConnections.isEmpty()) {
                log.info("Room \"{}\" was removed. There are no currently connected players.", runtimeId);
            } else {
                log.warn("Room \"{}\" was removed. \"{}\" players are still connected.",
                        runtimeId, allPlayerConnections.size());
            }
            return Multi.createFrom().iterable(allPlayerConnections)
                    .onItem().transformToUniAndConcatenate(playerConnection -> {
                        final var webSocketConnection = playerConnection.getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(DispatcherCloseReason.ROOM_REMOVED);
                        } else {
                            return Uni.createFrom().voidItem();
                        }
                    })
                    .collect().asList()
                    .replaceWith(new RemoveRoomResponse(Boolean.TRUE));
        } else {
            log.warn("Room \"{}\" was not found to be removed", runtimeId);
            return Uni.createFrom().item(new RemoveRoomResponse(Boolean.FALSE));
        }
    }
}
