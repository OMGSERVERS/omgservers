package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleWebSocketError;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketCloseReason;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorResponse;
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
class HandleWebSocketErrorMethodImpl implements HandleWebSocketErrorMethod {

    final RouterService routerService;
    final RoomService roomService;

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    @Override
    public Uni<HandleWebSocketErrorResponse> handleWebSocketError(final HandleWebSocketErrorRequest request) {
        log.debug("Handle websocket error, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();

        final var webSocketType = webSocketConnectionsContainer.getType(webSocketConnection);
        return switch (webSocketType) {
            case ROUTED -> closeRoutedConnection(webSocketConnection);
            case SERVER -> removeRoomConnection(webSocketConnection);
        };
    }

    Uni<HandleWebSocketErrorResponse> closeRoutedConnection(final WebSocketConnection serverConnection) {
        final var request = new CloseClientConnectionRequest(serverConnection,
                WebSocketCloseReason.ROUTED_CONNECTION_CLOSED);
        return routerService.closeClientConnection(request)
                .replaceWith(new HandleWebSocketErrorResponse());
    }

    Uni<HandleWebSocketErrorResponse> removeRoomConnection(final WebSocketConnection serverConnection) {
        final var request = new RemoveConnectionRequest(serverConnection);
        return roomService.removeConnection(request)
                .replaceWith(new HandleWebSocketErrorResponse());
    }
}
