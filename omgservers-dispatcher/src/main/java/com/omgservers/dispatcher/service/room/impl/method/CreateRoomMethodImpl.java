package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.CreateRoomRequest;
import com.omgservers.dispatcher.service.room.dto.CreateRoomResponse;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRooms;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateRoomMethodImpl implements CreateRoomMethod {

    final DispatcherRooms dispatcherRooms;

    @Override
    public Uni<CreateRoomResponse> execute(final CreateRoomRequest request) {
        log.trace("Requested, {}", request);

        final var runtimeConnection = request.getRuntimeConnection();

        final var dispatcherRoom = new DispatcherRoom(runtimeConnection);

        if (Objects.isNull(dispatcherRooms.putIfAbsent(dispatcherRoom))) {
            log.debug("Room for runtime \"{}\" was created", dispatcherRoom.getRuntimeId());
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.TRUE));
        } else {
            log.debug("Room was already created, room={}, runtimeConnection={}", dispatcherRoom, runtimeConnection);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.FALSE));
        }
    }
}
