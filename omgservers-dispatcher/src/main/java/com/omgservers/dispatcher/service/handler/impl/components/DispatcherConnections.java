package com.omgservers.dispatcher.service.handler.impl.components;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class DispatcherConnections {

    final Map<WebSocketConnection, DispatcherConnection> indexByWebsocketConnection;

    public DispatcherConnections() {
        indexByWebsocketConnection = new ConcurrentHashMap<>();
    }

    public List<DispatcherConnection> getAll() {
        return indexByWebsocketConnection.values().stream().toList();
    }

    public void put(final DispatcherConnection dispatcherConnection) {
        indexByWebsocketConnection.put(dispatcherConnection.getWebSocketConnection(),
                dispatcherConnection);
    }

    public DispatcherConnection get(final WebSocketConnection webSocketConnection) {
        return indexByWebsocketConnection.get(webSocketConnection);
    }

    public DispatcherConnection remove(final WebSocketConnection webSocketConnection) {
        return indexByWebsocketConnection.remove(webSocketConnection);
    }
}
