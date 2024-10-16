package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
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
        log.debug("Create room, request={}", request);

        final var runtimeConnection = request.getRuntimeConnection();

        final var dispatcherRoom = new DispatcherRoom(runtimeConnection);

        if (Objects.isNull(dispatcherRooms.putIfAbsent(dispatcherRoom))) {
            log.info("Room was created, runtimeId={}, runtimeConnection={}",
                    dispatcherRoom.getRuntimeId(), runtimeConnection);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.TRUE));
        } else {
            log.warn("Room was already created, runtimeId={}, runtimeConnection={}",
                    dispatcherRoom.getRuntimeId(), runtimeConnection);
            return Uni.createFrom().item(new CreateRoomResponse(Boolean.FALSE));
        }
    }
}
