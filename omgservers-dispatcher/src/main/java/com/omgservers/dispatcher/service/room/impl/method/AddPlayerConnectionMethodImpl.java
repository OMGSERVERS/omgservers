package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.dispatcher.component.ConnectionTypeEnum;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRooms;
import com.omgservers.schema.model.user.UserRoleEnum;
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
        log.trace("Requested, {}", request);

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
            log.debug("Room connection of player \"{}\" for runtime \"{}\" was added",
                    playerConnection.getSubject(), playerConnection.getRuntimeId());
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.TRUE));
        } else {
            log.debug("Player connection duplication detected, playerConnection={}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
