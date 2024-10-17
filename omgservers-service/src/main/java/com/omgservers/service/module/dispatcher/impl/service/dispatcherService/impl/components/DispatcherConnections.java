package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class DispatcherConnections {

    final Map<WebSocketConnection, DispatcherConnection> indexByWebSocketConnection;

    public DispatcherConnections() {
        indexByWebSocketConnection = new ConcurrentHashMap<>();
    }

    public List<DispatcherConnection> getAll() {
        return indexByWebSocketConnection.values().stream().toList();
    }

    public void put(final DispatcherConnection dispatcherConnection) {
        indexByWebSocketConnection.put(dispatcherConnection.getWebSocketConnection(), dispatcherConnection);
    }

    public DispatcherConnection get(final WebSocketConnection webSocketConnection) {
        return indexByWebSocketConnection.get(webSocketConnection);
    }

    public DispatcherConnection remove(final WebSocketConnection webSocketConnection) {
        return indexByWebSocketConnection.remove(webSocketConnection);
    }
}
