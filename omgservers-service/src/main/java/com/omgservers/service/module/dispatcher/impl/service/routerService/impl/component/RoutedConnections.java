package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class RoutedConnections {

    final Map<DispatcherConnection, WebSocketClientConnection> clientConnections;
    final Map<WebSocketClientConnection, DispatcherConnection> serverConnections;

    public RoutedConnections() {
        clientConnections = new HashMap<>();
        serverConnections = new HashMap<>();
    }

    public synchronized void put(final DispatcherConnection serverConnection,
                                 final WebSocketClientConnection clientConnection) {
        serverConnections.put(clientConnection, serverConnection);
        clientConnections.put(serverConnection, clientConnection);
    }

    public synchronized WebSocketClientConnection getClientConnection(final DispatcherConnection serverConnection) {
        return clientConnections.get(serverConnection);
    }

    public synchronized DispatcherConnection getServerConnection(final WebSocketClientConnection clientConnection) {
        return serverConnections.get(clientConnection);
    }

    public synchronized WebSocketClientConnection removeServerConnection(final DispatcherConnection serverConnection) {
        final var clientConnection = clientConnections.remove(serverConnection);
        if (Objects.nonNull(clientConnection)) {
            serverConnections.remove(clientConnection);
        }
        return clientConnection;
    }

    public synchronized DispatcherConnection removeClientConnection(final WebSocketClientConnection clientConnection) {
        final var serverConnection = serverConnections.remove(clientConnection);
        if (Objects.nonNull(serverConnection)) {
            clientConnections.remove(serverConnection);
        }
        return serverConnection;
    }
}
