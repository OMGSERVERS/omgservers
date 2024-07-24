package com.omgservers.router.websocket;

import com.omgservers.router.service.router.impl.RouterServiceImpl;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@WebSocket(path = "/omgservers/v1/entrypoint/websocket/connection")
public class RouterWebSocketEndpoint {

    final RouterServiceImpl routerServiceImpl;

    @OnOpen
    public Uni<Void> onOpen(final WebSocketConnection serverConnection) {
        return routerServiceImpl.routeServerConnection(serverConnection);
    }

    @OnClose
    public Uni<Void> onClose(final WebSocketConnection serverConnection,
                             final CloseReason serverCloseReason) {
        return routerServiceImpl.closeClientConnection(serverConnection, serverCloseReason);
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketConnection serverConnection,
                                   final String message) {
        return routerServiceImpl.transferServerTextMessage(serverConnection, message);
    }

    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketConnection serverConnection,
                                     final Buffer message) {
        return routerServiceImpl.transferServerBinaryMessage(serverConnection, message);
    }
}
