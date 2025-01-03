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
        log.trace("{}", request);

        final var runtimeConnection = request.getRuntimeConnection();
        final var runtimeId = runtimeConnection.getRuntimeId();

        final var dispatcherRoom = new DispatcherRoom(runtimeConnection);

        if (Objects.isNull(dispatcherRooms.putIfAbsent(dispatcherRoom))) {
            log.info("Room \"{}\" was created", runtimeId);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.TRUE));
        } else {
            log.info("Room \"{}\" was already created", runtimeId);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.FALSE));
        }
    }
}
