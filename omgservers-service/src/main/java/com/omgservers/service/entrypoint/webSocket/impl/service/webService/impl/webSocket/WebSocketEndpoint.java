package com.omgservers.service.entrypoint.webSocket.impl.service.webService.impl.webSocket;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webService.WebService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.WEBSOCKET})
@WebSocket(path = "/omgservers/v1/entrypoint/websocket/connection")
public class WebSocketEndpoint {

    final SecurityIdentity securityIdentity;
    final WebService webService;

    @OnOpen
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        log.info("Server connection was opened, id={}", webSocketConnection.id());
        final var request = new AddConnectionWebSocketRequest(securityIdentity, webSocketConnection);
        return webService.addConnection(request)
                .replaceWithVoid();
    }

    @OnClose
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection,
                             final CloseReason closeReason) {
        log.info("Server connection was closed, id={}, reason={}", webSocketConnection.id(), closeReason.getMessage());
        final var request = new HandleClosedConnectionWebSocketRequest(securityIdentity, webSocketConnection);
        return webService.handleCloseConnection(request)
                .replaceWithVoid();
    }

    @OnError
    public Uni<Void> onError(final WebSocketConnection webSocketConnection, final Throwable throwable) {
        log.info("Server connection was failed, id={}, {}:{}",
                webSocketConnection.id(), throwable.getClass().getSimpleName(), throwable.getMessage());
        final var request = new HandleWebSocketErrorRequest(securityIdentity, webSocketConnection, throwable);
        return webService.handleWebSocketError(request)
                .replaceWithVoid();
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        final var request = new HandleTextMessageWebSocketRequest(securityIdentity, webSocketConnection, message);
        return webService.handleTextMessage(request)
                .replaceWithVoid();
    }

    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer message) {
        final var request = new HandleBinaryMessageWebSocketRequest(securityIdentity, webSocketConnection, message);
        return webService.handleBinaryMessage(request)
                .replaceWithVoid();
    }
}
