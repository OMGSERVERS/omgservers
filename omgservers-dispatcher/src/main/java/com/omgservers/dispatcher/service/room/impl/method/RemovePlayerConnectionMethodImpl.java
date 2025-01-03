package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRooms;
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
        log.trace("{}", request);

        final var playerConnection = request.getPlayerConnection();
        final var subject = playerConnection.getSubject();
        final var runtimeId = playerConnection.getRuntimeId();

        final var playerRoom = dispatcherRooms.findPlayerRoom(playerConnection);
        if (Objects.nonNull(playerRoom)) {
            final var removed = playerRoom.remove(playerConnection);
            if (removed) {
                log.info("Player \"{}\" was removed from the room \"{}\"",  subject, runtimeId);
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.TRUE));
            } else {
                log.warn("Player \"{}\" was not found to be removed from the \"{}\"", subject, runtimeId);
                return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
            }
        } else {
            log.warn("Room \"{}\" was not found to add player \"{}\"", runtimeId, subject);
            return Uni.createFrom().item(new RemovePlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
