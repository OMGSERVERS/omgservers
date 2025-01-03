package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageResponse;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleBinaryMessageRequest request) {
        log.trace("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> transferServerBinaryMessage(dispatcherConnection, buffer);
                case SERVER -> transferRoomBinaryMessage(dispatcherConnection, buffer);
            }).replaceWithVoid();
        } else {
            log.error("Closing websocket \"{}\", dispatcher connection was not found", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerBinaryMessage(final DispatcherConnection dispatcherConnection,
                                             final Buffer buffer) {
        final var request = new TransferServerBinaryMessageRequest(dispatcherConnection, buffer);
        return routerService.transferServerBinaryMessage(request)
                .map(TransferServerBinaryMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomBinaryMessage(final DispatcherConnection dispatcherConnection,
                                           final Buffer buffer) {
        final var request = new TransferRoomBinaryMessageRequest(dispatcherConnection, buffer);
        return roomService.transferRoomBinaryMessage(request)
                .map(TransferRoomBinaryMessageResponse::getHandled);
    }
}
