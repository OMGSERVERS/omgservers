package com.omgservers.router.service.router;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;

public interface RouterService {
    Uni<Void> routeServerConnection(final WebSocketConnection serverConnection);

    Uni<Void> closeClientConnection(final WebSocketConnection serverConnection,
                                    final CloseReason serverCloseReason);

    Uni<Void> closeServerConnection(final WebSocketClientConnection clientConnection,
                                    final CloseReason clientCloseReason);

    Uni<Void> transferServerTextMessage(final WebSocketConnection serverConnection,
                                        final String message);

    Uni<Void> transferClientTextMessage(final WebSocketClientConnection clientConnection,
                                        final String message);

    Uni<Void> transferServerBinaryMessage(final WebSocketConnection serverConnection,
                                          final Buffer message);

    Uni<Void> transferClientBinaryMessage(final WebSocketClientConnection clientConnection,
                                          final Buffer message);
}
