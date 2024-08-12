package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleClosedConnection;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketCloseReason;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketResponse;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.server.service.router.RouterService;
import com.omgservers.service.server.service.router.dto.CloseClientConnectionRequest;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleClosedConnectionMethodImpl implements HandleClosedConnectionMethod {

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    final RouterService routerService;
    final RoomService roomService;

    @Override
    public Uni<HandleClosedConnectionWebSocketResponse> handleClosedConnection(
            final HandleClosedConnectionWebSocketRequest request) {
        log.debug("Handle closed connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();

        final var webSocketType = webSocketConnectionsContainer.getType(webSocketConnection);
        return switch (webSocketType) {
            case ROUTED -> closeRoutedConnection(webSocketConnection);
            case SERVER -> removeRoomConnection(webSocketConnection);
        };
    }

    Uni<HandleClosedConnectionWebSocketResponse> closeRoutedConnection(final WebSocketConnection serverConnection) {
        final var request = new CloseClientConnectionRequest(serverConnection,
                WebSocketCloseReason.ROUTED_CONNECTION_CLOSED);
        return routerService.closeClientConnection(request)
                .replaceWith(new HandleClosedConnectionWebSocketResponse());
    }

    Uni<HandleClosedConnectionWebSocketResponse> removeRoomConnection(final WebSocketConnection serverConnection) {
        final var request = new RemoveConnectionRequest(serverConnection);
        return roomService.removeConnection(request)
                .replaceWith(new HandleClosedConnectionWebSocketResponse());
    }

}
