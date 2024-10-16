package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.ConnectionTypeEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRooms;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AddPlayerConnectionMethodImpl implements AddPlayerConnectionMethod {

    final DispatcherRooms dispatcherRooms;

    @Override
    public Uni<AddPlayerConnectionResponse> execute(final AddPlayerConnectionRequest request) {
        log.debug("Add player connection, request={}", request);

        final var playerConnection = request.getPlayerConnection();
        final var runtimeId = playerConnection.getRuntimeId();
        final var userRole = playerConnection.getUserRole();

        if (!playerConnection.getConnectionType().equals(ConnectionTypeEnum.SERVER)) {
            log.error("Player connection type mismatch, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }

        final var playerRoom = dispatcherRooms.getRoom(runtimeId);
        if (Objects.isNull(playerRoom)) {
            log.warn("Room was not found to add player connection, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }

        if (!userRole.equals(UserRoleEnum.PLAYER)) {
            log.error("Player connection role mismatch, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }

        if (Objects.isNull(playerRoom.putIfAbsent(playerConnection))) {
            log.info("Player connection was added to the room, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.TRUE));
        } else {
            log.warn("Player connection duplication detected, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
