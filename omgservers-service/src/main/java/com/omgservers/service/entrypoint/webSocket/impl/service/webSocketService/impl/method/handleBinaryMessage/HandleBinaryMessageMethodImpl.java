package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleBinaryMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.server.service.router.RouterService;
import com.omgservers.service.server.service.router.dto.TransferServerBinaryMessageRequest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    @Override
    public Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(
            final HandleBinaryMessageWebSocketRequest request) {
        log.debug("Handle binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var webSocketType = webSocketConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return switch (webSocketType.get()) {
                case ROUTED -> transferBinaryMessage(webSocketConnection, message);
                case SERVER -> handleBinaryMessage(webSocketConnection, message);
            };
        } else {
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR)
                    .replaceWith(new HandleBinaryMessageWebSocketResponse());
        }
    }

    Uni<HandleBinaryMessageWebSocketResponse> transferBinaryMessage(final WebSocketConnection webSocketConnection,
                                                                    final Buffer message) {
        final var request = new TransferServerBinaryMessageRequest(webSocketConnection, message);
        return routerService.transferServerBinaryMessage(request)
                .replaceWith(new HandleBinaryMessageWebSocketResponse());
    }

    Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(final WebSocketConnection webSocketConnection,
                                                                  final Buffer message) {
        final var request = new HandleBinaryMessageRequest(webSocketConnection, message);
        return roomService.handleBinaryMessage(request)
                .replaceWith(new HandleBinaryMessageWebSocketResponse());
    }
}
