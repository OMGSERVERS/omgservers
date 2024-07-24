package com.omgservers.router.websocket;

import com.omgservers.router.service.router.RouterService;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocketClient;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@WebSocketClient(path = "/omgservers/v1/entrypoint/websocket/connection")
public class RouterWebSocketClient {

    final RouterService routerService;

    @OnClose
    public Uni<Void> onClose(final WebSocketClientConnection clientConnection,
                             final CloseReason clientCloseReason) {
        return routerService.closeServerConnection(clientConnection, clientCloseReason);
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketClientConnection clientConnection,
                                   final String message) {
        return routerService.transferClientTextMessage(clientConnection, message);
    }

    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketClientConnection clientConnection,
                                     final Buffer message) {
        return routerService.transferClientBinaryMessage(clientConnection, message);
    }
}
