package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageResponse;
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
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleTextMessageRequest request) {
        log.trace("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> transferServerTextMessage(dispatcherConnection, message);
                case SERVER -> transferRoomTextMessage(dispatcherConnection, message);
            }).replaceWithVoid();
        } else {
            log.error("Closing websocket \"{}\", dispatcher connection was not found", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerTextMessage(final DispatcherConnection dispatcherConnection,
                                           final String message) {
        final var request = new TransferServerTextMessageRequest(dispatcherConnection, message);
        return routerService.transferServerTextMessage(request)
                .map(TransferServerTextMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomTextMessage(final DispatcherConnection dispatcherConnection,
                                         final String message) {
        final var request = new TransferRoomTextMessageRequest(dispatcherConnection, message);
        return roomService.transferRoomTextMessage(request)
                .map(TransferRoomTextMessageResponse::getHandled);
    }
}
