package com.omgservers.connector.server.handler.component;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ConnectorConnections {

    final Map<WebSocketConnection, ConnectorConnection> indexByWebsocketConnection;

    public ConnectorConnections() {
        indexByWebsocketConnection = new ConcurrentHashMap<>();
    }

    public List<ConnectorConnection> getAll() {
        return indexByWebsocketConnection.values().stream().toList();
    }

    public void put(final ConnectorConnection connectorConnection) {
        indexByWebsocketConnection.put(connectorConnection.getWebSocketConnection(),
                connectorConnection);
    }

    public ConnectorConnection get(final WebSocketConnection webSocketConnection) {
        return indexByWebsocketConnection.get(webSocketConnection);
    }

    public ConnectorConnection remove(final WebSocketConnection webSocketConnection) {
        return indexByWebsocketConnection.remove(webSocketConnection);
    }
}
