package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleTextMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.server.service.router.RouterService;
import com.omgservers.service.server.service.router.dto.TransferServerTextMessageRequest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    @Override
    public Uni<HandleTextMessageWebSocketResponse> handleTextMessage(final HandleTextMessageWebSocketRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var webSocketType = webSocketConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return switch (webSocketType.get()) {
                case ROUTED -> transferTextMessage(webSocketConnection, message);
                case SERVER -> handleTextMessage(webSocketConnection, message);
            };
        } else {
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR)
                    .replaceWith(new HandleTextMessageWebSocketResponse());
        }
    }

    Uni<HandleTextMessageWebSocketResponse> transferTextMessage(final WebSocketConnection webSocketConnection,
                                                                final String message) {
        final var request = new TransferServerTextMessageRequest(webSocketConnection, message);
        return routerService.transferServerTextMessage(request)
                .replaceWith(new HandleTextMessageWebSocketResponse());
    }

    Uni<HandleTextMessageWebSocketResponse> handleTextMessage(final WebSocketConnection webSocketConnection,
                                                              final String message) {
        final var request = new HandleTextMessageRequest(webSocketConnection, message);
        return roomService.handleTextMessage(request)
                .replaceWith(new HandleTextMessageWebSocketResponse());
    }
}
