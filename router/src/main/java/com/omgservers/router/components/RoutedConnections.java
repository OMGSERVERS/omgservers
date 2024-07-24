package com.omgservers.router.components;

import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class RoutedConnections {

    final Map<WebSocketConnection, WebSocketClientConnection> routedConnections;
    final Map<WebSocketClientConnection, WebSocketConnection> originConnections;

    public RoutedConnections() {
        routedConnections = new HashMap<>();
        originConnections = new HashMap<>();
    }

    public synchronized void put(final WebSocketConnection serverConnection,
                                 final WebSocketClientConnection clientConnection) {
        originConnections.put(clientConnection, serverConnection);
        routedConnections.put(serverConnection, clientConnection);
    }

    public synchronized WebSocketClientConnection getRoutedConnection(final WebSocketConnection serverConnection) {
        return routedConnections.get(serverConnection);
    }

    public synchronized WebSocketConnection getOriginConnection(final WebSocketClientConnection clientConnection) {
        return originConnections.get(clientConnection);
    }

    public synchronized WebSocketClientConnection remove(final WebSocketConnection serverConnection) {
        final var routedConnection = routedConnections.remove(serverConnection);
        originConnections.remove(routedConnection);
        return routedConnection;
    }

    public synchronized WebSocketConnection remove(final WebSocketClientConnection clientConnection) {
        final var originConnection = originConnections.remove(clientConnection);
        routedConnections.remove(originConnection);
        return originConnection;
    }
}
