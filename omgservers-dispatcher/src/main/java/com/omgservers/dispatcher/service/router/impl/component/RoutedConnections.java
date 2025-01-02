package com.omgservers.dispatcher.service.router.impl.component;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class RoutedConnections {

    final Map<DispatcherConnection, WebSocketClientConnection> indexByDispatcherConnection;
    final Map<WebSocketClientConnection, DispatcherConnection> indexByWebSocketClientConnection;

    public RoutedConnections() {
        indexByDispatcherConnection = new HashMap<>();
        indexByWebSocketClientConnection = new HashMap<>();
    }

    public synchronized void put(final DispatcherConnection serverConnection,
                                 final WebSocketClientConnection clientConnection) {
        indexByWebSocketClientConnection.put(clientConnection, serverConnection);
        indexByDispatcherConnection.put(serverConnection, clientConnection);
    }

    public synchronized WebSocketClientConnection getClientConnection(final DispatcherConnection serverConnection) {
        return indexByDispatcherConnection.get(serverConnection);
    }

    public synchronized DispatcherConnection getServerConnection(final WebSocketClientConnection clientConnection) {
        return indexByWebSocketClientConnection.get(clientConnection);
    }

    public synchronized WebSocketClientConnection removeServerConnection(final DispatcherConnection serverConnection) {
        final var clientConnection = indexByDispatcherConnection.remove(serverConnection);
        if (Objects.nonNull(clientConnection)) {
            indexByWebSocketClientConnection.remove(clientConnection);
        }
        return clientConnection;
    }

    public synchronized DispatcherConnection removeClientConnection(final WebSocketClientConnection clientConnection) {
        final var serverConnection = indexByWebSocketClientConnection.remove(clientConnection);
        if (Objects.nonNull(serverConnection)) {
            indexByDispatcherConnection.remove(serverConnection);
        }
        return serverConnection;
    }
}
