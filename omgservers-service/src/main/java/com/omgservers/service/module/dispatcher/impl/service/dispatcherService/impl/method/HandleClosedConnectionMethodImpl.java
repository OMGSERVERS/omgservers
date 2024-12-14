package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
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

    final DispatcherModule dispatcherModule;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleClosedConnectionRequest request) {
        log.trace("Requested, {}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> closeRoutedConnection(dispatcherConnection, closeReason)
                        .invoke(closed -> {
                            if (closed) {
                                log.debug("Routed connection was closed, dispatcherConnection={}, closeReason={}",
                                        dispatcherConnection,
                                        closeReason.toString());
                            }
                        });
                case SERVER -> handleDispatcherConnection(dispatcherConnection)
                        .invoke(result -> {
                            if (result) {
                                log.info("Room connection for runtime {} was closed, closeReason={}",
                                        dispatcherConnection.getRuntimeId(), closeReason);
                            }
                        });
            })
                    .invoke(closed -> dispatcherConnections.remove(webSocketConnection))
                    .replaceWithVoid();
        } else {
            log.error("Corresponding dispatcher connection was not found to handle closed connection, skip operation " +
                    "id={}", webSocketConnection.id());
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> closeRoutedConnection(final DispatcherConnection serverConnection,
                                       final CloseReason closeReason) {
        final var request = new CloseClientConnectionRequest(serverConnection, closeReason);
        return dispatcherModule.getRouterService().closeClientConnection(request)
                .map(CloseClientConnectionResponse::getClosed);
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var runtimeId = dispatcherConnection.getRuntimeId();

            final var request = new RemoveRoomRequest(runtimeId);
            return dispatcherModule.getRoomService().removeRoom(request)
                    .map(RemoveRoomResponse::getRemoved);
        } else {
            final var request = new RemovePlayerConnectionRequest(dispatcherConnection);
            return dispatcherModule.getRoomService().removePlayerConnection(request)
                    .map(RemovePlayerConnectionResponse::getRemoved);
        }
    }
}
