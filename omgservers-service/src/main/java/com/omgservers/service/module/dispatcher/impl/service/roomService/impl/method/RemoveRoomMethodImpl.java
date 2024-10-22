package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
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
        log.debug("Requested, {}", request);

        final var runtimeId = request.getRuntimeId();

        final var dispatcherRoom = dispatcherRooms.removeRoom(runtimeId);
        if (Objects.nonNull(dispatcherRoom)) {
            final var allPlayerConnections = dispatcherRoom.getAllPlayerConnections();

            log.info("Room was removed, runtimeId={}, allPlayerConnections={}", runtimeId, allPlayerConnections.size());
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
            log.warn("Room was not found to remove, runtimeId={}", runtimeId);
            return Uni.createFrom().item(new RemoveRoomResponse(Boolean.FALSE));
        }
    }
}
