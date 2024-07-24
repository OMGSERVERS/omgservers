package com.omgservers.service.service.room.impl.method.createRoom;

import com.omgservers.service.service.room.dto.CreateRoomRequest;
import com.omgservers.service.service.room.impl.component.RoomInstance;
import com.omgservers.service.service.room.impl.component.RoomsContainer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateRoomMethodImpl implements CreateRoomMethod {

    final RoomsContainer roomsContainer;

    @Override
    public Uni<Void> creteRoom(final CreateRoomRequest request) {
        log.debug("Create room, request={}", request);

        final var runtimeId = request.getRuntimeId();
        if (roomsContainer.getRoom(runtimeId).isPresent()) {
            log.debug("Room was already created, skip operation, runtimeId={}", runtimeId);
        } else {
            final var room = new RoomInstance(runtimeId);
            roomsContainer.replaceRoom(room);
        }

        return Uni.createFrom().voidItem();
    }
}
