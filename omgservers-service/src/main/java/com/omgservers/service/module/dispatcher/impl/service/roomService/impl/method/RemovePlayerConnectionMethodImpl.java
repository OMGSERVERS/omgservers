package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RemovePlayerConnectionMethodImpl implements RemovePlayerConnectionMethod {

    final DispatcherRooms dispatcherRooms;

    @Override
    public Uni<RemovePlayerConnectionResponse> execute(final RemovePlayerConnectionRequest request) {
        log.trace("Requested, {}", request);

        final var playerConnection = request.getPlayerConnection();

        final var playerRoom = dispatcherRooms.findPlayerRoom(playerConnection);
        if (Objects.nonNull(playerRoom)) {
            final var removed = playerRoom.remove(playerConnection);
            if (removed) {
                log.debug("Room connection of player {} for runtime {} was removed",
                        playerConnection.getSubject(), playerConnection.getRuntimeId());
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.TRUE));
            } else {
                log.debug("Player connection was not found to remove, playerConnection={}", playerConnection);
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
            }
        } else {
            log.debug("Room was not found to remove player connection, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
