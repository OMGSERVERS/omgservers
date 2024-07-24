package com.omgservers.router.service.router.impl;

import com.omgservers.router.components.RoutedConnections;
import com.omgservers.router.operation.getConfig.RouterConfig;
import com.omgservers.router.service.router.RouterService;
import com.omgservers.router.websocket.RouterWebSocketClient;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OpenClientConnections;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import io.quarkus.websockets.next.WebSocketConnector;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RouterServiceImpl implements RouterService {

    final WebSocketConnector<RouterWebSocketClient> webSocketConnector;
    final OpenClientConnections openClientConnections;
    final RoutedConnections routedConnections;
    final RouterConfig routerConfig;
    final JWTParser jwtParser;

    @Override
    public Uni<Void> routeServerConnection(final WebSocketConnection serverConnection) {
        final var query = serverConnection.handshakeRequest().query();

        log.info("Route server connection, id={}, query={}", serverConnection.id(), query);
        final var serviceUri = routerConfig.serviceUri();
        return webSocketConnector.baseUri(serviceUri).connect()
                .invoke(clientConnection -> routedConnections.put(serverConnection, clientConnection))
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> closeClientConnection(final WebSocketConnection serverConnection,
                                           final CloseReason serverCloseReason) {
        log.info("Server connection is closed, id={}", serverConnection.id());
        final var clientConnection = routedConnections.remove(serverConnection);
        return clientConnection.close(serverCloseReason);
    }

    @Override
    public Uni<Void> closeServerConnection(final WebSocketClientConnection clientConnection,
                                           final CloseReason clientCloseReason) {
        log.info("Client connection is closed, id={}, reason={}:{}", clientConnection.id(),
                clientCloseReason.getCode(), clientCloseReason.getMessage());
        final var serverConnection = routedConnections.remove(clientConnection);
        return serverConnection.close(clientCloseReason);
    }

    @Override
    public Uni<Void> transferServerTextMessage(final WebSocketConnection serverConnection,
                                               final String message) {
        final var clientConnection = routedConnections.getRoutedConnection(serverConnection);
        return clientConnection.sendText(message);
    }

    @Override
    public Uni<Void> transferClientTextMessage(final WebSocketClientConnection clientConnection,
                                               final String message) {
        final var serverConnection = routedConnections.getOriginConnection(clientConnection);
        return serverConnection.sendText(message);
    }

    @Override
    public Uni<Void> transferServerBinaryMessage(final WebSocketConnection serverConnection,
                                                 final Buffer message) {
        final var clientConnection = routedConnections.getRoutedConnection(serverConnection);
        return clientConnection.sendBinary(message);
    }

    @Override
    public Uni<Void> transferClientBinaryMessage(final WebSocketClientConnection clientConnection,
                                                 final Buffer message) {
        final var serverConnection = routedConnections.getOriginConnection(clientConnection);
        return serverConnection.sendBinary(message);
    }
}
