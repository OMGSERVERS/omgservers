package com.omgservers.service.service.dispatcher.impl.method.createRoom;

import com.omgservers.service.service.dispatcher.dto.CreateRoomRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomResponse;
import com.omgservers.service.service.dispatcher.impl.component.RoomInstance;
import com.omgservers.service.service.dispatcher.impl.component.RoomsContainer;
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
    public Uni<CreateRoomResponse> creteRoom(final CreateRoomRequest request) {
        log.debug("Create room, request={}", request);

        final var runtimeId = request.getRuntimeId();
        if (roomsContainer.getRoom(runtimeId).isPresent()) {
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.FALSE));
        } else {
            final var room = new RoomInstance(runtimeId);
            roomsContainer.replaceRoom(room);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.TRUE));
        }
    }
}
