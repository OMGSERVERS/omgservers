package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class DispatcherConnections {

    final Map<WebSocketConnection, DispatcherConnection> connections;

    public DispatcherConnections() {
        connections = new ConcurrentHashMap<>();
    }

    public void put(final DispatcherConnection dispatcherConnection) {
        connections.put(dispatcherConnection.getWebSocketConnection(), dispatcherConnection);
    }

    public DispatcherConnection get(final WebSocketConnection webSocketConnection) {
        return connections.get(webSocketConnection);
    }

    public DispatcherConnection remove(final WebSocketConnection webSocketConnection) {
        return connections.remove(webSocketConnection);
    }
}
