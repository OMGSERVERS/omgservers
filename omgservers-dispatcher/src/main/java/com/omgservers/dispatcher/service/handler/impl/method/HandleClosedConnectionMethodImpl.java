package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomRequest;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleClosedConnectionMethodImpl implements HandleClosedConnectionMethod {

    final RouterService routerService;
    final RoomService roomService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleClosedConnectionRequest request) {
        log.trace("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> closeRoutedConnection(dispatcherConnection, closeReason);
                case SERVER -> handleDispatcherConnection(dispatcherConnection);
            }).invoke(closed -> dispatcherConnections.remove(webSocketConnection)).replaceWithVoid();
        } else {
            log.error("Dispatcher connection was not found, skipping operation for \"{}\"", webSocketConnection.id());
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> closeRoutedConnection(final DispatcherConnection serverConnection,
                                       final CloseReason closeReason) {
        final var request = new CloseClientConnectionRequest(serverConnection, closeReason);
        return routerService.closeClientConnection(request)
                .map(CloseClientConnectionResponse::getClosed);
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var runtimeId = dispatcherConnection.getRuntimeId();

            final var request = new RemoveRoomRequest(runtimeId);
            return roomService.removeRoom(request)
                    .map(RemoveRoomResponse::getRemoved);
        } else {
            final var request = new RemovePlayerConnectionRequest(dispatcherConnection);
            return roomService.removePlayerConnection(request)
                    .map(RemovePlayerConnectionResponse::getRemoved);
        }
    }
}
