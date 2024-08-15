package com.omgservers.service.service.router.component;

import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class RouterConnectionsContainer {

    final Map<WebSocketConnection, WebSocketClientConnection> clientConnections;
    final Map<WebSocketClientConnection, WebSocketConnection> serverConnections;

    public RouterConnectionsContainer() {
        clientConnections = new HashMap<>();
        serverConnections = new HashMap<>();
    }

    public synchronized void put(final WebSocketConnection serverConnection,
                                 final WebSocketClientConnection clientConnection) {
        serverConnections.put(clientConnection, serverConnection);
        clientConnections.put(serverConnection, clientConnection);
    }

    public synchronized WebSocketClientConnection getClientConnection(final WebSocketConnection serverConnection) {
        return clientConnections.get(serverConnection);
    }

    public synchronized WebSocketConnection getServerConnection(final WebSocketClientConnection clientConnection) {
        return serverConnections.get(clientConnection);
    }

    public synchronized WebSocketClientConnection remove(final WebSocketConnection serverConnection) {
        final var clientConnection = clientConnections.remove(serverConnection);
        serverConnections.remove(clientConnection);
        return clientConnection;
    }

    public synchronized WebSocketConnection remove(final WebSocketClientConnection clientConnection) {
        final var serverConnection = serverConnections.remove(clientConnection);
        clientConnections.remove(serverConnection);
        return serverConnection;
    }
}
